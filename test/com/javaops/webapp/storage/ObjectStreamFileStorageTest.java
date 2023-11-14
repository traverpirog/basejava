package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategies.ObjectStreamStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest{
    protected ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
