package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
        if (STORAGE_LIMIT < size) {
            throw new StorageException(r.getUuid(), "ERROR: storage переполнен");
        } else if (index > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(index, r);
            size++;
        }
    }

    @Override
    public final void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            size--;
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int size() {
        return size;
    }

    public abstract void saveResume(int index, Resume resume);

    public abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
