package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SaveReadStrategy {
    void writeFile(OutputStream outputStream, Resume resume) throws IOException;
    Resume readFile(InputStream outputStream) throws IOException;
}
