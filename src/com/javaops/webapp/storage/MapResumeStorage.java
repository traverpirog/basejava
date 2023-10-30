package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get(String.valueOf(searchKey));
    }

    @Override
    protected void saveResume(Object searchKey, Resume r) {
        storage.put(String.valueOf(searchKey), r);
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.replace(String.valueOf(searchKey), r);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(String.valueOf(searchKey));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(String.valueOf(searchKey));
    }

    @Override
    protected Object findSearchKey(String uuid, String fullName) {
        return new Resume(uuid, fullName);
    }
}
