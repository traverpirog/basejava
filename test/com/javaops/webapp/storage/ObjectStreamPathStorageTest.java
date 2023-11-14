package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategies.ObjectStreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    protected ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStrategy()));
    }
}
