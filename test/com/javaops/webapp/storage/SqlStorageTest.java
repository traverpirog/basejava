package com.javaops.webapp.storage;

import com.javaops.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    protected SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUsername(),
                Config.getInstance().getDbPassword()));
    }
}
