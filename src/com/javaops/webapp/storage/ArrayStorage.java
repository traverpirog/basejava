package com.javaops.webapp.storage;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public int saveResume(int index) {
        return size;
    }

    @Override
    public void deleteResume(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
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
