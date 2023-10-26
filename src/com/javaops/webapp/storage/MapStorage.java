package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

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
    protected Object findIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
