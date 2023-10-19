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
    private final Storage STORAGE;
    private final String UUID = "uuid";
    private final String UUID1 = "uuid1";
    private final String UUID2 = "uuid2";
    private final String UUID3 = "uuid3";
    private final String UUID_NOT_EXIST = "dummy";
    private final Resume RESUME = new Resume(UUID);
    private final Resume RESUME1 = new Resume(UUID1);
    private final Resume RESUME2 = new Resume(UUID2);
    private final Resume RESUME3 = new Resume(UUID3);
    private final Resume[] EXPECTED = new Resume[]{RESUME1, RESUME2, RESUME3};

    public AbstractArrayStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @BeforeEach
    public void setUp() throws ExistStorageException {
        STORAGE.clear();
        STORAGE.save(RESUME1);
        STORAGE.save(RESUME2);
        STORAGE.save(RESUME3);
    }

    @Test
    public void clear() {
        STORAGE.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, STORAGE.getAll());
    }

    @Test
    public void save() {
        STORAGE.save(RESUME);
        assertSize(4);
        assertGet(RESUME);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            Resume resume = new Resume(UUID1);
            STORAGE.save(resume);
        });
    }

    @Test
    public void saveWhenFull() {
        STORAGE.clear();
        try {
            int STORAGE_LIMIT = AbstractArrayStorage.STORAGE_LIMIT;
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                Resume resume = new Resume();
                STORAGE.save(resume);
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
        assertThrows(StorageException.class, () -> {
            STORAGE.save(RESUME);
        });
    }

    @Test
    public void update() {
        Resume resume = STORAGE.get(UUID1);
        STORAGE.update(resume);
        assertSame(this.RESUME1, STORAGE.get(resume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.update(new Resume(UUID_NOT_EXIST)));
    }

    @Test
    public void delete() {
        STORAGE.delete(UUID1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> {
            STORAGE.get(UUID1);
        });
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.delete(UUID_NOT_EXIST));
    }

    @Test
    public void get() {
        Resume[] resumes = STORAGE.getAll();
        for (Resume resume : resumes) {
            assertGet(resume);
        }
    }

    public void assertGet(Resume resume) {
        assertNotNull(STORAGE.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.get(UUID_NOT_EXIST));
    }

    @Test
    public void getAll() {
        assertArrayEquals(EXPECTED, STORAGE.getAll());
    }

    @Test
    public void size() {
        assertSize(STORAGE.size());
    }

    public void assertSize(int size) {
        Assertions.assertEquals(STORAGE.size(), size);
    }
}