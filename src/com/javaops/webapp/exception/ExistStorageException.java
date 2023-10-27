package com.javaops.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String key) {
        super(key, "ERROR: Резюме " + key + " уже существует!");
    }
}
