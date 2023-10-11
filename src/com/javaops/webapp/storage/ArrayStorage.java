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
        if (isResume(r.getUuid())) {
            System.out.println("ERROR: Резюме " + r.getUuid() + " уже существует!");
            return;
        }

        if (storage.length > size) {
            storage[size] = r;
            size++;
            return;
        }

        System.out.println("ERROR: storage переполнен");
    }

    public void update(Resume r) {
        if (!isResume(r.getUuid())) {
            System.out.println("ERROR: Резюме " + r.getUuid() + " не найдено!");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                storage[i] = r;
                break;
            }
        }
    }

    public Resume get(String uuid) {
        if (!isResume(uuid)) {
            System.out.println("ERROR: Резюме " + uuid + " не найдено!");
            return null;
        }
        return Arrays.stream(storage).limit(size).filter(resume -> resume.getUuid().equals(uuid)).findFirst().get();
    }

    public void delete(String uuid) {
        if (!isResume(uuid)) {
            System.out.println("ERROR: Резюме " + uuid + " не найдено!");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    private boolean isResume(String uuid) {
        return Arrays.stream(storage).limit(size).anyMatch(resume1 -> uuid.equals(resume1.getUuid()));
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
