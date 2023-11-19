package com.javaops.webapp.storage.strategies;

import com.javaops.webapp.model.*;
import com.javaops.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializerStrategy {
    private final XmlParser xmlParser;
    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, TextSection.class, Company.class, CompanySection.class, ListSection.class, Period.class);
    }

    @Override
    public void writeFile(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
