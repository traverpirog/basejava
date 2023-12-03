package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.ContactType;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUsername, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            }
            executeBatch(connection, r, "INSERT INTO contact (value, resume_uuid, type) VALUES (?, ?, ?)");
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                int result = preparedStatement.executeUpdate();
                if (result != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            executeBatch(connection, r, "UPDATE contact c SET value = ? WHERE c.resume_uuid = ? AND c.type = ?");
            return null;
        });
    }

    private void executeBatch(Connection connection, Resume r, String command) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                preparedStatement.setString(1, entry.getValue());
                preparedStatement.setString(2, r.getUuid());
                preparedStatement.setString(3, entry.getKey().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                        " LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        " WHERE r.uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                                resultSet.getString("value"));
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                throw new NotExistStorageException(uuid);
            }
            return result;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> resumeList = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resumeList.add(new Resume(resultSet.getString("uuid"),
                            resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact c")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    for (Resume resume : resumeList) {
                        if (resume.getUuid().equals(resultSet.getString("resume_uuid"))) {
                            resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                                    resultSet.getString("value"));
                        }
                    }
                }
            }
            return resumeList;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }
}
