package com.javaops.webapp.storage.strategies;

import com.javaops.webapp.model.Resume;
import com.javaops.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializerStrategy {
    @Override
    public void writeFile(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
