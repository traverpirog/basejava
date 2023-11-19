package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategies.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonStreamSerializer()));
    }
}
