package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume r) {
        if (findIndex(r.getUuid()) > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
        }
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return new ArrayList<>(storage).toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }
}
