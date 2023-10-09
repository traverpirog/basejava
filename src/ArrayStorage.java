import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
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
        return Arrays.stream(storage)
                .filter(resume -> resume != null && resume.uuid.equals(uuid))
                .findFirst()
                .orElse(null);
    }

    void delete(String uuid) {
        int position = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                position = i;
            }
        }

        for (int i = position; i < storage.length - 1; i++) {
            if (storage[i + 1] != null) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    int size() {
        return (int) Arrays.stream(storage).distinct().count() - 1;
    }
}
