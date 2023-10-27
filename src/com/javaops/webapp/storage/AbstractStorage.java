package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final Resume get(String uuid, String fullName) {
        Object searchKey = getExistingSearchKey(uuid, fullName);
        return getResume(searchKey);
    }

    @Override
    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid(), r.getFullName());
        saveResume(searchKey, r);
    }

    @Override
    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid(), r.getFullName());
        updateResume(searchKey, r);
    }

    @Override
    public final void delete(String uuid, String fullName) {
        Object searchKey = getExistingSearchKey(uuid, fullName);
        deleteResume(searchKey);
    }

    private Object getExistingSearchKey(String uuid, String fullName) {
        Object searchKey = findSearchKey(uuid, fullName);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object getNotExistingSearchKey(String uuid, String fullName) {
        Object searchKey = findSearchKey(uuid, fullName);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Resume getResume(Object searchKey);

    protected abstract void saveResume(Object searchKey, Resume r);

    protected abstract void updateResume(Object searchKey, Resume r);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object findSearchKey(String uuid, String fullName);

    protected abstract boolean isExist(Object searchKey);
}