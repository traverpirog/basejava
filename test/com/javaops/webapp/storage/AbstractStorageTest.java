package com.javaops.webapp.storage;

import com.javaops.webapp.Config;
import com.javaops.webapp.ResumeTestData;
import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {
    protected final Storage STORAGE;
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected final String UUID1 = String.valueOf(UUID.randomUUID());
    protected final String FULL_NAME_1 = "Иванов Иван Иванович";
    protected final String UUID2 = String.valueOf(UUID.randomUUID());
    protected final String FULL_NAME_2 = "Петров Петр Петрович";
    protected final String UUID3 = String.valueOf(UUID.randomUUID());
    protected final String FULL_NAME_3 = "Владимиров Владимир Владимирович";
    protected final String UUID4 = String.valueOf(UUID.randomUUID());
    protected final String FULL_NAME_4 = "Ибрагимов Ибрагим Ибрагимович";
    protected final String UUID_NOT_EXIST = "dummy";
    protected final String FULL_NAME_NOT_EXIST = "Алексеев Алексей Алексеевич";
    protected final Resume RESUME1 = ResumeTestData.getResume(UUID1, FULL_NAME_1);
    protected final Resume RESUME2 = ResumeTestData.getResume(UUID2, FULL_NAME_2);
    protected final Resume RESUME3 = ResumeTestData.getResume(UUID3, FULL_NAME_3);
    protected final Resume RESUME4 = ResumeTestData.getResume(UUID4, FULL_NAME_4);
    protected final List<Resume> EXPECTED = Arrays.asList(RESUME1, RESUME2, RESUME3);

    protected AbstractStorageTest(Storage storage) {
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
        assertEquals(new ArrayList<>(), STORAGE.getAllSorted());
    }

    @Test
    public void save() {
        STORAGE.save(RESUME4);
        assertSize(4);
        assertGet(RESUME4);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> STORAGE.save(ResumeTestData.getResume(UUID1, FULL_NAME_1)));
    }

    @Test
    public void update() {
        Resume resume = STORAGE.get(UUID1);
        STORAGE.update(resume);
        assertEquals(this.RESUME1, STORAGE.get(resume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.update(ResumeTestData.getResume(UUID_NOT_EXIST, FULL_NAME_NOT_EXIST)));
    }

    @Test
    public void delete() {
        STORAGE.delete(UUID1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> STORAGE.get(UUID1));
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
        EXPECTED.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(EXPECTED, STORAGE.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(STORAGE.size());
    }

    private void assertGet(Resume resume) {
        assertNotNull(STORAGE.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, STORAGE.size());
    }
}