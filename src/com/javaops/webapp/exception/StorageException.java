package com.javaops.webapp.exception;

public class StorageException extends RuntimeException {
    private final String key;

    public StorageException(String key, String message) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
