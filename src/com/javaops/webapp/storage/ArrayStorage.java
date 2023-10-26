package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveResume(Object searchKey, Resume r) {
        if (STORAGE_LIMIT == size) {
            throw new StorageException(r.getUuid(), "ERROR: storage переполнен");
        }
        storage[size] = r;
        size++;
    }

    @Override
    public void deleteResume(Object searchKey) {
        storage[(int) searchKey] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Object findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
