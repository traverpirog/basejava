package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    Resume get(String uuid, String fullName);

    void delete(String uuid, String fullName);

    List<Resume> getAllSorted();

    int size();
}
