package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.strategies.SaveReadStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SaveReadStrategy saveReadStrategy;

    protected FileStorage(File directory, SaveReadStrategy saveReadStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.saveReadStrategy = saveReadStrategy;
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
            deleteResume(file);
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
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateResume(file, r);
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            saveReadStrategy.writeFile(new BufferedOutputStream(new FileOutputStream(file)), r);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return saveReadStrategy.readFile(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File was not deleted", file.getName());
        }
    }

    @Override
    protected List<Resume> getStorage() {
        List<Resume> list = new ArrayList<>();
        for (File file : getListFiles()) {
            Resume resume = getResume(file);
            list.add(resume);
        }
        return list;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Invalid path", "");
        }
        return files;
    }
}
