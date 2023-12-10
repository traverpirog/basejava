package com.javaops.webapp;

import com.javaops.webapp.model.*;
import com.javaops.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private static Resume RESUME;
    private static final Map<ContactType, String> CONTACTS = new EnumMap<>(ContactType.class);
    private static final Map<SectionType, AbstractSection> SECTIONS = new EnumMap<>(SectionType.class);
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
    private static final Period PERIOD_1 = new Period(DateUtil.of(2020, Month.MAY), DateUtil.of(2021, Month.DECEMBER), "Webmaster", null);
    private static final Period PERIOD_2 = new Period(DateUtil.of(2020, Month.OCTOBER), DateUtil.of(2022, Month.DECEMBER), "Webmaster", "lorem ipsum");
    private static final List<Period> PERIODS = new ArrayList<>();
    private static final List<Period> PERIODS_2 = new ArrayList<>();
    private static final Company COMPANY_1 = new Company("VK", null, PERIODS);
    private static final Company COMPANY_2 = new Company("YANDEX", "ya.ru", PERIODS_2);
    private static final List<Company> COMPANIES = new ArrayList<>();
    private static final List<String> ACHIEVEMENT = new ArrayList<>();
    private static final List<String> QUALIFICATIONS = new ArrayList<>();

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

        for (ContactType contact : ContactType.values()) {
            switch (contact) {
                case PHONE -> CONTACTS.put(ContactType.PHONE, PHONE);
                case MOBILE -> CONTACTS.put(ContactType.MOBILE, MOBILE);
                case HOME_PHONE -> CONTACTS.put(ContactType.HOME_PHONE, HOME_PHONE);
                case SKYPE -> CONTACTS.put(ContactType.SKYPE, SKYPE);
                case EMAIL -> CONTACTS.put(ContactType.EMAIL, EMAIL);
                //case LINKEDIN -> CONTACTS.put(ContactType.LINKEDIN, LINKEDIN);
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
                //case EXPERIENCE -> SECTIONS.put(SectionType.EXPERIENCE, new CompanySection(COMPANIES));
                //case EDUCATION -> SECTIONS.put(SectionType.EDUCATION, new CompanySection(COMPANIES));
            }
        }
    }

    public static void addInfo(Resume resume) {
        CONTACTS.forEach(resume::addContact);
        SECTIONS.forEach(resume::addSection);
    }

    public static Resume getResume(String fullName) {
        RESUME = new Resume(fullName);
        addInfo(RESUME);
        return RESUME;
    }

    public static Resume getResume(String uuid, String fullName) {
        RESUME = new Resume(uuid, fullName);
        addInfo(RESUME);
        return RESUME;
    }
}
