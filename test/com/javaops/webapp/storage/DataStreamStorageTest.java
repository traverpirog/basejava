package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategies.DataStreamSerializer;

public class DataStreamStorageTest extends AbstractStorageTest{
    protected DataStreamStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
