package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;
    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        for (File file : getListFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(File file, Resume r) {
        try {
            file.createNewFile();
            writeFile(file, r);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            writeFile(file, r);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void writeFile(File file, Resume r) throws IOException;

    @Override
    protected Resume getResume(File file) {
        return readFile(file);
    }

    protected abstract Resume readFile(File file);

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getStorage() {
        return Arrays.stream(getListFiles()).map(this::readFile).toList();
    }

    private File[] getListFiles() {
        return directory.listFiles();
    }
}
