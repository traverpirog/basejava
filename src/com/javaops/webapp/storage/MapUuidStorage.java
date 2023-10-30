package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {
    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void saveResume(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected Object findSearchKey(String uuid, String fullName) {
        return uuid;
    }
}
