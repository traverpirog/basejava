package com.javaops.webapp.storage;

import com.javaops.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    protected SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}
