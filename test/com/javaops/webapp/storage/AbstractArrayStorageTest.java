package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws ExistStorageException {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume());
        storage.save(new Resume());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() throws ExistStorageException {
        storage.save(new Resume());
        assertEquals(4, storage.size());
    }

    @Test
    public void update() throws NotExistStorageException {

    }

    @Test
    public void delete() throws NotExistStorageException {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }
}