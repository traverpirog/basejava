package com.javaops.webapp.storage;

import com.javaops.webapp.ResumeTestData;
import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractStorageTest {
    protected final Storage STORAGE;
    protected final String UUID = "uuid";
    protected final String FULL_NAME = "Иванов Иван Иванович";
    protected final String UUID1 = "uuid1";
    protected final String FULL_NAME_1 = "Петров Петр Петрович";
    protected final String UUID2 = "uuid2";
    protected final String FULL_NAME_2 = "Владимиров Владимир Владимирович";
    protected final String UUID3 = "uuid3";
    protected final String FULL_NAME_3 = "Ибрагимов Ибрагим Ибрагимович";
    protected final String UUID_NOT_EXIST = "dummy";
    protected final String FULL_NAME_NOT_EXIST = "Алексеев Алексей Алексеевич";
    protected final Resume RESUME = ResumeTestData.getResume(UUID, FULL_NAME);
    protected final Resume RESUME1 = ResumeTestData.getResume(UUID1, FULL_NAME_1);
    protected final Resume RESUME2 = ResumeTestData.getResume(UUID2, FULL_NAME_2);
    protected final Resume RESUME3 = ResumeTestData.getResume(UUID3, FULL_NAME_3);
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
        STORAGE.save(RESUME);
        assertSize(4);
        assertGet(RESUME);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            STORAGE.save(ResumeTestData.getResume(UUID1, FULL_NAME_1));
        });
    }

    @Test
    public void update() {
        Resume resume = STORAGE.get(UUID1, FULL_NAME_1);
        STORAGE.update(resume);
        assertSame(this.RESUME1, STORAGE.get(resume.getUuid(), resume.getFullName()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.update(ResumeTestData.getResume(UUID_NOT_EXIST, FULL_NAME_NOT_EXIST)));
    }

    @Test
    public void delete() {
        STORAGE.delete(UUID1, FULL_NAME_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> {
            STORAGE.get(UUID1, FULL_NAME_1);
        });
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.delete(UUID_NOT_EXIST, FULL_NAME_NOT_EXIST));
    }

    @Test
    public void get() {
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> STORAGE.get(UUID_NOT_EXIST, FULL_NAME_NOT_EXIST));
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
        assertNotNull(STORAGE.get(resume.getUuid(), resume.getFullName()));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, STORAGE.size());
    }
}