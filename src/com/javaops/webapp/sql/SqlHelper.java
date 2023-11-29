package com.javaops.webapp.sql;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static <T> T execute(ConnectionFactory connectionFactory, String command, SqlFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            return function.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static <T> void executeSave(ConnectionFactory connectionFactory, String command, String uuid, SqlFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            function.execute(preparedStatement);
        } catch (SQLException e) {
            throw new ExistStorageException(uuid);
        }
    }
}
