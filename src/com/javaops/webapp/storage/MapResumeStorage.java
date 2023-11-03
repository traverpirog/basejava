package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {
    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected void saveResume(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(Resume resume, Resume r) {
        storage.replace(resume.getUuid(), r);
    }

    @Override
    protected void deleteResume(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }
}
