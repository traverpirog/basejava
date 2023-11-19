package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategies.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamSerializer()));
    }
}
