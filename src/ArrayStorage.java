import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int filledSize;

    void clear() {
        Arrays.fill(storage, 0, filledSize, null);
        this.filledSize = 0;
    }

    void save(Resume r) {
        storage[filledSize] = r;
        this.filledSize++;
    }

    Resume get(String uuid) {
        return Arrays.stream(storage)
                .limit(filledSize)
                .filter(resume -> resume.uuid.equals(uuid))
                .findFirst()
                .orElse(null);
    }

    void delete(String uuid) {
        for (int i = 0; i < filledSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[filledSize - 1];
                storage[filledSize - 1] = null;
                this.filledSize--;
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
        return filledSize;
    }
}
