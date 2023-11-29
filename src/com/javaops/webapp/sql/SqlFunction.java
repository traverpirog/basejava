package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
