package com.javaops.webapp.storage;

import com.javaops.webapp.models.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (STORAGE_LIMIT < size) {
            System.out.println("ERROR: storage переполнен");
        } else if (findIndex(r.getUuid()) != -1) {
            System.out.println("ERROR: Резюме " + r.getUuid() + " уже существует!");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index == -1) {
            System.out.println("ERROR: Резюме " + r.getUuid() + " не найдено!");
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("ERROR: Резюме " + uuid + " не найдено!");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (findIndex(uuid) == -1) {
            System.out.println("ERROR: Резюме " + uuid + " не найдено!");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    private int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
