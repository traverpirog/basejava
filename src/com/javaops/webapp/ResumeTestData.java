package com.javaops.webapp;

import com.javaops.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private static final Resume RESUME = new Resume("uuid1", "Igor Aver");
    private static final Map<ContactType, String> CONTACTS = new HashMap<>();
    private static final Map<SectionType, AbstractSection> SECTIONS = new HashMap<>();
    private static final String PHONE = "+7 (999) 999-99-99";
    private static final String MOBILE = "+7 (999) 999-99-99";
    private static final String HOME_PHONE = "+7 (999) 999-99-99";
    private static final String SKYPE = "travorpirog";
    private static final String EMAIL = "travorpirog@yandex.ru";
    private static final String LINKEDIN = "travorpirog";
    private static final String GITHUB = "https://github.com/traverpirog";
    private static final String STACKOVERFLOW = "travorpirog";
    private static final String WEBSITE = "https://vk.com";
    private static final String OBJECTIVE = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    private static final String PERSONAL = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
    private static final Period PERIOD_1 = new Period(LocalDate.of(2020, 11, 28), LocalDate.now(), "Webmaster", "lorem ipsum");
    private static final Period PERIOD_2 = new Period(LocalDate.of(2020, 11, 28), LocalDate.now(), "Webmaster", "lorem ipsum");
    private static final List<Period> PERIODS = new ArrayList<>();
    private static final List<Period> PERIODS_2 = new ArrayList<>();
    private static final Company COMPANY_1 = new Company("VK", "vk.com", PERIODS);
    private static final Company COMPANY_2 = new Company("YANDEX", "ya.ru", PERIODS_2);
    private static final List<Company> COMPANIES = new ArrayList<>();
    private static final CompanySection COMPANY_SECTION_1 = new CompanySection(COMPANIES);
    private static final List<String> ACHIEVEMENT = new ArrayList<>();
    private static final List<String> QUALIFICATIONS = new ArrayList<>();
    private static final List<String> EXPERIENCE = new ArrayList<>();
    private static final List<String> EDUCATION = new ArrayList<>();

    static {
        ACHIEVEMENT.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        ACHIEVEMENT.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников. ");
        ACHIEVEMENT.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");

        QUALIFICATIONS.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        QUALIFICATIONS.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        QUALIFICATIONS.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB ");

        PERIODS.add(PERIOD_1);
        PERIODS.add(PERIOD_2);
        PERIODS_2.add(PERIOD_2);
        PERIODS_2.add(PERIOD_2);

        COMPANIES.add(COMPANY_1);
        COMPANIES.add(COMPANY_2);

        EXPERIENCE.add(COMPANIES.toString());
        EDUCATION.add(COMPANIES.toString());
    }

    public static void main(String[] args) {
        for (ContactType contact : ContactType.values()) {
            switch (contact) {
                case PHONE -> CONTACTS.put(ContactType.PHONE, PHONE);
                case MOBILE -> CONTACTS.put(ContactType.MOBILE, MOBILE);
                case HOME_PHONE -> CONTACTS.put(ContactType.HOME_PHONE, HOME_PHONE);
                case SKYPE -> CONTACTS.put(ContactType.SKYPE, SKYPE);
                case EMAIL -> CONTACTS.put(ContactType.EMAIL, EMAIL);
                case LINKEDIN -> CONTACTS.put(ContactType.LINKEDIN, LINKEDIN);
                case GITHUB -> CONTACTS.put(ContactType.GITHUB, GITHUB);
                case STACKOVERFLOW -> CONTACTS.put(ContactType.STACKOVERFLOW, STACKOVERFLOW);
                case WEBSITE -> CONTACTS.put(ContactType.WEBSITE, WEBSITE);
            }
        }

        for (SectionType section : SectionType.values()) {
            switch (section) {
                case PERSONAL -> SECTIONS.put(SectionType.PERSONAL, new TextSection(PERSONAL));
                case OBJECTIVE -> SECTIONS.put(SectionType.OBJECTIVE, new TextSection(OBJECTIVE));
                case ACHIEVEMENT -> SECTIONS.put(SectionType.ACHIEVEMENT, new ListSection(ACHIEVEMENT));
                case QUALIFICATIONS -> SECTIONS.put(SectionType.QUALIFICATIONS, new ListSection(QUALIFICATIONS));
                case EXPERIENCE -> SECTIONS.put(SectionType.EXPERIENCE, new ListSection(EXPERIENCE));
                case EDUCATION -> SECTIONS.put(SectionType.EDUCATION, new ListSection(EDUCATION));
            }
        }

        RESUME.setContacts(CONTACTS);
        RESUME.setSections(SECTIONS);

        System.out.println(RESUME);
    }
}
