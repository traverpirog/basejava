package com.javaops.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super(uuid, "ERROR: Резюме " + uuid + " уже существует!");
    }
}
