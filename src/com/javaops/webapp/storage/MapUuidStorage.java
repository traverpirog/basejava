package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {
    @Override
    protected Resume getResume(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveResume(String uuid, Resume r) {
        storage.put(uuid, r);
    }

    @Override
    protected void updateResume(String uuid, Resume r) {
        storage.replace(uuid, r);
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }
}
