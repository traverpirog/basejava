package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;
import org.junit.Assert;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private final Storage storage = new ArrayStorage();
    private final String UUID_1 = "uuid1";
    private final String UUID_2 = "uuid2";
    private final String UUID_3 = "uuid3";

    @org.junit.Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @org.junit.Test
    public void clear() {
    }

    @org.junit.Test
    public void save() {
    }

    @org.junit.Test
    public void update() {
    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void get() {
    }

    @org.junit.Test
    public void getAll() {
    }

    @org.junit.Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}