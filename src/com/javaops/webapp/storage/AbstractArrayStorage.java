package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getStorage() {
        List<Resume> resumes = new ArrayList<>();
        Collections.addAll(resumes, Arrays.copyOf(storage, size));
        return resumes;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void updateResume(Integer index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index > -1;
    }
}
