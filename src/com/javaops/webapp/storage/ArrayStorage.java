package com.javaops.webapp.storage;

import com.javaops.webapp.models.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        storage[size] = r;
        size++;
    }

    public void update(Resume r) {

    }

    public Resume get(String uuid) {
        return Arrays.stream(storage).limit(size).filter(resume -> resume.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.stream(storage).limit(size).toArray(Resume[]::new);
    }

    public int size() {
        return size;
    }
}
