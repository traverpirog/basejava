package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final List<Resume> resumes = new ArrayList<>();
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUsername, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.execute(connectionFactory, "DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void save(Resume r) {
        SqlHelper.executeSave(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", r.getUuid(), preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            return preparedStatement.executeUpdate();
        });
    }

    @Override
    public void update(Resume r) {
        SqlHelper.execute(connectionFactory, "UPDATE resume r SET full_name = ? WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                throw new NotExistStorageException(r.getUuid());
            }
            return result;
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.execute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.execute(connectionFactory, "DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
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
        return SqlHelper.execute(connectionFactory, "SELECT * FROM resume ORDER BY full_name ASC, uuid ASC", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid").strip(), resultSet.getString("full_name").strip()));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return SqlHelper.execute(connectionFactory, "SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        });
    }
}
