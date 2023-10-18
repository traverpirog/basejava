package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractArrayStorageTest {
    private final Storage storage;
    private final String uuid1 = "uuid1";
    private final String uuid2 = "uuid2";
    private final String uuid3 = "uuid3";
    private final String uuidNotExist = "dummy";
    private final Resume resume1 = new Resume(uuid1);
    private final Resume resume2 = new Resume(uuid2);
    private final Resume resume3 = new Resume(uuid3);
    private final Resume[] expected = new Resume[]{resume1, resume2, resume3};

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws ExistStorageException {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    public void save() {
        storage.clear();
        storage.save(resume1);
        assertSize(1);
        assertGet(resume1);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            Resume resume = new Resume(uuid1);
            storage.save(resume);
        });
    }

    @Test
    public void saveWhenFull() {
        storage.clear();
        try {
            int STORAGE_LIMIT = AbstractArrayStorage.STORAGE_LIMIT;
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                Resume resume = new Resume();
                storage.save(resume);
                if (STORAGE_LIMIT < storage.size()) {
                    fail("Переполнение произошло раньше времени");
                }
            }
            storage.save(resume1);
        } catch (StorageException e) {
            assertThrows(StorageException.class, () -> {
                throw new StorageException(resume1.getUuid(), "storage переполнен");
            });
        }

    }

    @Test
    public void update() {
        Resume resume = storage.get(uuid1);
        storage.update(resume);
        assertSame(this.resume1, storage.get(resume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(uuidNotExist)));
    }

    @Test
    public void delete() {
        storage.delete(uuid1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> {
            storage.get(uuid1);
        });
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(uuidNotExist));
    }

    @Test
    public void get() {
        Resume[] resumes = storage.getAll();
        for (Resume resume : resumes) {
            assertGet(resume);
        }
    }

    public void assertGet(Resume resume) {
        assertNotNull(storage.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(uuidNotExist));
    }

    @Test
    public void getAll() {
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void size() {
        assertSize(storage.size());
    }

    public void assertSize(int size) {
        Assertions.assertEquals(storage.size(), size);
    }
}