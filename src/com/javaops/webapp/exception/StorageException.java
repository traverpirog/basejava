package com.javaops.webapp.exception;

public class StorageException extends RuntimeException {
    private final String key;

    public StorageException(String key, String message) {
        super(message);
        this.key = key;
    }

    public StorageException(String key, String message, Exception exception) {
        super(message, exception);
        this.key = key;
    }

    public StorageException(String message, Exception exception) {
        this(message, null, exception);
    }

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public String getKey() {
        return key;
    }
}
