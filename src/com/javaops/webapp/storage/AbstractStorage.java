package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
    protected static final Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    @Override
    public final Resume get(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public final void save(Resume r) {
        T searchKey = getNotExistingSearchKey(r.getUuid());
        saveResume(searchKey, r);
    }

    @Override
    public final void update(Resume r) {
        T searchKey = getExistingSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    @Override
    public final void delete(String uuid) {
        T searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    private T getExistingSearchKey(String uuid) {
        T searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private T getNotExistingSearchKey(String uuid) {
        T searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getStorage();
        resumeList.sort(resumeComparator);
        return resumeList;
    }

    protected abstract List<Resume> getStorage();

    protected abstract Resume getResume(T searchKey);

    protected abstract void saveResume(T searchKey, Resume r);

    protected abstract void updateResume(T searchKey, Resume r);

    protected abstract void deleteResume(T searchKey);

    protected abstract T findSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);
}
