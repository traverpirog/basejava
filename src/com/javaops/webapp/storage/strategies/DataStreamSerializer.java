package com.javaops.webapp.storage.strategies;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements SerializerStrategy {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void writeFile(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts.keySet(), dos, o -> {
                dos.writeUTF(o.name());
                dos.writeUTF(contacts.get(o));
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.keySet(), dos, o -> {
                dos.writeUTF(o.name());
                switch (o) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) sections.get(o)).getText());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeListSection(dos, ((ListSection) sections.get(o)).getList());
                    case EXPERIENCE, EDUCATION ->
                            writeCompanySection(dos, ((CompanySection) sections.get(o)).getCompanies());
                }
            });
        }
    }

    private <T> void writeWithException(Collection<T> tCollection, DataOutputStream dos, CheckedConsumer<T> checkedConsumer) throws IOException {
        Objects.requireNonNull(dos);
        for (T item : tCollection) {
            checkedConsumer.accept(item);
        }
    }

    private void writeListSection(DataOutputStream dos, List<String> list) throws IOException {
        dos.writeInt(list.size());
        writeWithException(list, dos, dos::writeUTF);
    }

    private void writeCompanySection(DataOutputStream dos, List<Company> list) throws IOException {
        dos.writeInt(list.size());
        writeWithException(list, dos, company -> {
            dos.writeUTF(company.getName());
            dos.writeUTF(Objects.requireNonNullElse(company.getWebsite(), ""));
            writePeriods(dos, company.getPeriods());
        });
    }

    private void writePeriods(DataOutputStream dos, List<Period> list) throws IOException {
        dos.writeInt(list.size());
        writeWithException(list, dos, period -> {
            dos.writeUTF(period.getDateFrom().format(FORMATTER));
            dos.writeUTF(period.getDateTo().format(FORMATTER));
            dos.writeUTF(period.getTitle());
            dos.writeUTF(Objects.requireNonNullElse(period.getDescription(), ""));
        });
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            resume.addSection(sectionType, new ListSection(readListSection(dis)));
                    case EXPERIENCE, EDUCATION ->
                            resume.addSection(sectionType, new CompanySection(readCompanySection(dis)));
                }
            }
            return resume;
        }
    }

    private List<String> readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private List<Company> readCompanySection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Company> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String companyName = dis.readUTF();
            String website = dis.readUTF();
            list.add(new Company(companyName, website.isEmpty() ? null : website, readPeriods(dis)));
        }
        return list;
    }

    private List<Period> readPeriods(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Period> periods = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LocalDate dateFrom = LocalDate.parse(dis.readUTF(), FORMATTER);
            LocalDate dateTo = LocalDate.parse(dis.readUTF(), FORMATTER);
            String title = dis.readUTF();
            String description = dis.readUTF();
            periods.add(new Period(dateFrom, dateTo, title, description.isEmpty() ? null : description));
        }
        return periods;
    }
}
