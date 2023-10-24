package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveResume(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    public void deleteResume(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
