package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.StorageException;
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
    public final void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (STORAGE_LIMIT == size) {
            throw new StorageException(r.getUuid(), "ERROR: storage переполнен");
        } else if (index > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(index, r);
            size++;
        }
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
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage[index] = r;
    }

    public abstract void saveResume(int index, Resume resume);

    public abstract void deleteResume(int index);
}
