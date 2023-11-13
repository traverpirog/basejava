package com.javaops.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest{
    protected ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
