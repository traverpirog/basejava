package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapStorageTest {
    private final Storage STORAGE = new MapStorage();
    private final String UUID = "uuid";
    private final String UUID1 = "uuid1";
    private final String UUID2 = "uuid2";
    private final String UUID3 = "uuid3";
    private final String UUID_NOT_EXIST = "dummy";
    private final Resume RESUME = new Resume(UUID);
    private final Resume RESUME1 = new Resume(UUID1);
    private final Resume RESUME2 = new Resume(UUID2);
    private final Resume RESUME3 = new Resume(UUID3);
    private final ListStorage EXPECTED = new ListStorage();

    {
        EXPECTED.save(RESUME1);
        EXPECTED.save(RESUME2);
        EXPECTED.save(RESUME3);
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
        assertArrayEquals(new ArrayList<>().toArray(), STORAGE.getAll());
    }

    @Test
    public void save() {
        STORAGE.save(RESUME);
        assertGet(RESUME);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            STORAGE.save(new Resume(UUID1));
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
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.get(UUID_NOT_EXIST));
    }

    @Test
    public void getAll() {

    }

    private void assertGet(Resume resume) {
        assertNotNull(STORAGE.get(resume.getUuid()));
    }
}
