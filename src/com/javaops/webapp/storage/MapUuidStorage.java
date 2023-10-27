package com.javaops.webapp.storage;

public class MapUuidStorage extends AbstractMapStorage {
    @Override
    protected Object findSearchKey(String uuid, String fullName) {
        return uuid;
    }
}
