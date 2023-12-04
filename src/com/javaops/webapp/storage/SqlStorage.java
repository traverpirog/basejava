package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactType;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
            executeBatch(connection, r);
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
            executeBatch(connection, r);
            return null;
        });
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
                        addContactToResume(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeList = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = new Resume(resultSet.getString("uuid"),
                            resultSet.getString("full_name"));
                    resumeList.put(resume.getUuid(), resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact c")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContactToResume(resultSet, resumeList.get(resultSet.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(resumeList.values());
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
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void executeBatch(Connection connection, Resume r) throws SQLException {
        for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
            if (entry.getValue().isEmpty()) {
                deleteContact(connection, entry.getKey().name(), r.getUuid());
            } else if (!isExistContact(connection, entry.getKey().name(), r.getUuid())) {
                saveContact(connection, entry.getKey().name(), entry.getValue(), r.getUuid());
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE contact c SET value = ? WHERE c.resume_uuid = ? AND c.type = ?")) {
                    preparedStatement.setString(1, entry.getValue());
                    preparedStatement.setString(2, r.getUuid());
                    preparedStatement.setString(3, entry.getKey().name());
                    preparedStatement.executeUpdate();
                }
            }

        }
    }

    private void deleteContact(Connection connection, String type, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE type = ? AND resume_uuid = ?")) {
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, uuid);
            preparedStatement.executeUpdate();
        }
    }

    private boolean isExistContact(Connection connection, String type, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE type = ? AND resume_uuid = ?")) {
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    private void saveContact(Connection connection, String type, String value, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, value);
            preparedStatement.setString(3, uuid);
            preparedStatement.executeUpdate();
        }
    }

    private void addContactToResume(ResultSet resultSet, Resume resume) throws SQLException {
        resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                resultSet.getString("value"));
    }
}
