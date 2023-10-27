package com.javaops.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String key) {
        super(key, "ERROR: Резюме " + key + " не найдено!");
    }
}
