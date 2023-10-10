import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int countResumes;

    void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    void save(Resume r) {
        storage[countResumes] = r;
        countResumes++;
    }

    Resume get(String uuid) {
        return Arrays.stream(storage)
                .limit(countResumes)
                .filter(resume -> resume.uuid.equals(uuid))
                .findFirst()
                .orElse(null);
    }

    void delete(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[countResumes - 1];
                storage[countResumes - 1] = null;
                countResumes--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage).limit(countResumes).toArray(Resume[]::new);
    }

    int size() {
        return countResumes;
    }
}
