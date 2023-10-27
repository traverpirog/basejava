package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest{
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveWhenFull() {
        try {
            int STORAGE_LIMIT = AbstractArrayStorage.STORAGE_LIMIT;
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                STORAGE.save(new Resume(FULL_NAME));
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
        assertThrows(StorageException.class, () -> {
            STORAGE.save(RESUME);
        });
    }
}
