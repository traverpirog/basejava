package com.javaops.webapp.sql;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public record SqlHelper(ConnectionFactory connectionFactory) {
    public <T> T execute(String command, SqlFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            return function.execute(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
