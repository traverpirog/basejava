package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractArrayStorageTest {
    private final int STORAGE_LIMIT = 10000;
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
    public void save() {
        Resume resume = new Resume();
        storage.save(resume);
        assertNotNull(storage.get(resume.getUuid()));
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            Resume resume = new Resume("uuid1");
            storage.save(resume);
        });
    }

    @Test
    public void saveWhenFull() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());

            if (storage.size() > STORAGE_LIMIT) {
                fail("Переполнение произошло раньше времени");
            }
        }
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            storage.save(new Resume());
        });
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid1");
        storage.update(resume);
        assertNotNull(storage.get(resume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        assertEquals(2, storage.size());
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    public void get() {
        assertNotNull(storage.get("uuid1"));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    public void getAll() {
        assertNotNull(storage.getAll());
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }
}