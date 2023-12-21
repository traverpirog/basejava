package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.*;
import com.javaops.webapp.sql.CheckedSqlConsumer;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;
import com.javaops.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUsername, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
            writeContacts(connection, r);
            writeSections(connection, r);
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
            clearContactsAndSection(connection, r);
            writeContacts(connection, r);
            writeSections(connection, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                setDataFromResultSet(preparedStatement, resultSet -> {
                    addContactToResume(resultSet, resume);
                });
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                setDataFromResultSet(preparedStatement, resultSet -> {
                    addSectionToResume(resultSet, resume);
                });
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeList = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
                setDataFromResultSet(preparedStatement, resultSet -> {
                    Resume resume = new Resume(resultSet.getString("uuid"),
                            resultSet.getString("full_name"));
                    resumeList.put(resume.getUuid(), resume);
                });
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact c")) {
                setDataFromResultSet(preparedStatement, resultSet -> {
                    addContactToResume(resultSet, resumeList.get(resultSet.getString("resume_uuid")));
                });
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section s")) {
                setDataFromResultSet(preparedStatement, resultSet -> {
                    addSectionToResume(resultSet, resumeList.get(resultSet.getString("resume_uuid")));
                });
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

    private void clearContactsAndSection(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?;" +
                " DELETE FROM section WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getUuid());
            preparedStatement.execute();
        }
    }

    private void writeContacts(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                preparedStatement.setString(1, entry.getKey().name());
                preparedStatement.setString(2, entry.getValue());
                preparedStatement.setString(3, r.getUuid());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void writeSections(Connection connection, Resume r) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                preparedStatement.setString(1, entry.getKey().name());
                preparedStatement.setString(2, JsonParser.write(entry.getValue(), AbstractSection.class));
                preparedStatement.setString(3, r.getUuid());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }


    private void addContactToResume(ResultSet resultSet, Resume resume) throws SQLException {
        String type = resultSet.getString("type");
        String value = resultSet.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(type), value);
        }
    }

    private void addSectionToResume(ResultSet resultSet, Resume resume) throws SQLException {
        String type = resultSet.getString("type");
        String value = resultSet.getString("value");
        if (type != null && value != null) {
            resume.addSection(SectionType.valueOf(type), JsonParser.read(value, AbstractSection.class));
        }
    }

    private void setDataFromResultSet(PreparedStatement preparedStatement, CheckedSqlConsumer<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            consumer.accept(resultSet);
        }
    }
}
