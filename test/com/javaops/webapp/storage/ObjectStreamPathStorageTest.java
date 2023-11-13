package com.javaops.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    protected ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStrategy()));
    }
}
