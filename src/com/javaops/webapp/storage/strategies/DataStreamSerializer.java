package com.javaops.webapp.storage.strategies;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void writeFile(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            Map<ContactType, String> contacts = resume.getContacts();
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                dos.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeListSection(dos, ((ListSection) section.getValue()).getList());
                    case EXPERIENCE, EDUCATION ->
                            writeCompanySection(dos, ((CompanySection) section.getValue()).getCompanies());
                }
            }
        }
    }

    private void writeListSection(DataOutputStream dos, List<String> list) throws IOException {
        dos.writeInt(list.size());
        for (String text : list) dos.writeUTF(text);
    }

    private void writeCompanySection(DataOutputStream dos, List<Company> list) throws IOException {
        dos.writeInt(list.size());
        for (Company company : list) {
            dos.writeUTF(company.getName());
            dos.writeUTF(company.getWebsite());
            writePeriods(dos, company.getPeriods());
        }
    }

    private void writePeriods(DataOutputStream dos, List<Period> list) throws IOException {
        dos.writeInt(list.size());
        for (Period period : list) {
            dos.writeUTF(period.getDateFrom().format(FORMATTER));
            dos.writeUTF(period.getDateTo().format(FORMATTER));
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
        }
    }

    @Override
    public Resume readFile(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++)
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                System.out.println(sectionType);
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
            list.add(new Company(dis.readUTF(), dis.readUTF(), readPeriods(dis)));
        }
        return list;
    }

    private List<Period> readPeriods(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Period> periods = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            periods.add(new Period(LocalDate.parse(dis.readUTF(), FORMATTER), LocalDate.parse(dis.readUTF(), FORMATTER), dis.readUTF(), dis.readUTF()));
        }
        return periods;
    }
}
