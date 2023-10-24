package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index > -1) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateResume(index, r);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(int index, Resume r);

    protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
