import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
    }

    void save(Resume r) {
        for (int i = storage.length - 1; i > 0; i--) {
            Resume tmp = storage[i - 1];
            storage[i - 1] = storage[i];
            storage[i] = tmp;
        }
        storage[0] = r;
    }

    Resume get(String uuid) {
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return null;
    }

    int size() {
        return 0;
    }
}
