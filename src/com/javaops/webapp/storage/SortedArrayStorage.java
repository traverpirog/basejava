package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveResume(Object searchKey, Resume r) {
        if (STORAGE_LIMIT == size) {
            throw new StorageException(r.getUuid(), "ERROR: storage переполнен");
        }
        int index = (int) searchKey;
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    public void deleteResume(Object searchKey) {
        int index = (int) searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected Object findSearchKey(String uuid, String fullName) {
        Resume searchKey = new Resume(uuid, fullName);
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
    }
}
