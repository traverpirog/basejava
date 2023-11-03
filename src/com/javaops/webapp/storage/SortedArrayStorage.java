package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> comparator = Comparator.comparing(Resume::getUuid);

    @Override
    public void saveResume(Integer index, Resume r) {
        if (STORAGE_LIMIT == size) {
            throw new StorageException(r.getUuid(), "ERROR: storage переполнен");
        }
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    public void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, comparator);
    }
}
