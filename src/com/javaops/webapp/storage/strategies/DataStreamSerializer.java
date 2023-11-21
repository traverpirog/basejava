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
            writeWithException(contacts.keySet(), dos, o -> {
                dos.writeUTF(o.name());
                dos.writeUTF(contacts.get(o));
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
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
        dos.writeInt(tCollection.size());
        for (T item : tCollection) {
            checkedConsumer.accept(item);
        }
    }

    private void writeListSection(DataOutputStream dos, List<String> list) throws IOException {
        writeWithException(list, dos, dos::writeUTF);
    }

    private void writeCompanySection(DataOutputStream dos, List<Company> list) throws IOException {
        writeWithException(list, dos, company -> {
            dos.writeUTF(company.getName());
            dos.writeUTF(Objects.requireNonNullElse(company.getWebsite(), ""));
            writePeriods(dos, company.getPeriods());
        });
    }

    private void writePeriods(DataOutputStream dos, List<Period> list) throws IOException {
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
            readWithException(dis, o -> {
                resume.addContact(ContactType.valueOf(((DataInputStream) o).readUTF()), ((DataInputStream) o).readUTF());
            });
            readWithException(dis, o -> {
                SectionType sectionType = SectionType.valueOf(((DataInputStream) o).readUTF());
                DataInputStream dataInputStream = (DataInputStream) o;
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE ->
                            resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            resume.addSection(sectionType, new ListSection(readListSection(dataInputStream)));
                    case EXPERIENCE, EDUCATION ->
                            resume.addSection(sectionType, new CompanySection(readCompanySection(dataInputStream)));
                }
            });
            return resume;
        }
    }

    private <T> void readWithException(DataInputStream dataInputStream, CheckedConsumer<T> checkedConsumer) throws IOException {
        Objects.requireNonNull(dataInputStream);
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            checkedConsumer.accept((T) dataInputStream);
        }
    }

    private List<String> readListSection(DataInputStream dis) throws IOException {
        List<String> list = new ArrayList<>();
        readWithException(dis, o -> {
            list.add(((DataInputStream) o).readUTF());
        });
        return list;
    }

    private List<Company> readCompanySection(DataInputStream dis) throws IOException {
        List<Company> list = new ArrayList<>();
        readWithException(dis, o -> {
            DataInputStream dataInputStream = (DataInputStream) o;
            String companyName = dataInputStream.readUTF();
            String website = dataInputStream.readUTF();
            list.add(new Company(companyName, website.isEmpty() ? null : website, readPeriods((DataInputStream) o)));
        });
        return list;
    }

    private List<Period> readPeriods(DataInputStream dis) throws IOException {
        List<Period> periods = new ArrayList<>();
        readWithException(dis, o -> {
            DataInputStream dataInputStream = (DataInputStream) o;
            LocalDate dateFrom = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
            LocalDate dateTo = LocalDate.parse(dataInputStream.readUTF(), FORMATTER);
            String title = dataInputStream.readUTF();
            String description = dataInputStream.readUTF();
            periods.add(new Period(dateFrom, dateTo, title, description.isEmpty() ? null : description));
        });
        return periods;
    }
}
