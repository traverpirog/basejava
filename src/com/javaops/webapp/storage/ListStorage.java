package com.javaops.webapp.storage;

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
    protected Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void saveResume(Object searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((int) searchKey);
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
    protected Object findIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0 && storage.size() > 0 && storage.get(index) != null;
    }
}
