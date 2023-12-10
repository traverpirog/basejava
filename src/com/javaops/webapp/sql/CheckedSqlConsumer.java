package com.javaops.webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface CheckedSqlConsumer<T> {
    void accept(T t) throws SQLException;
}
