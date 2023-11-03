package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage<T> extends AbstractStorage<T> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getStorage() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
