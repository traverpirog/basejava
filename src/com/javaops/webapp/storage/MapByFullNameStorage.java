package com.javaops.webapp.storage;

public class MapByFullNameStorage extends AbstractMapStorage {
    @Override
    protected Object findSearchKey(String uuid, String fullName) {
        return fullName;
    }
}
