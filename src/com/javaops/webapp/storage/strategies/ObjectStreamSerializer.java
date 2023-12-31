package com.javaops.webapp.storage.strategies;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements SerializerStrategy {
    @Override
    public void writeFile(OutputStream outputStream, Resume r) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Read resume error", null, e);
        }
    }
}
