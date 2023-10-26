package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0 && storage.length > 0 && storage[index] != null;
    }
}
