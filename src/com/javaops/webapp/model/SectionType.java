package com.javaops.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml(String value) {
            return value.replaceAll("\n", "<br>");
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(String value) {
            return value.replaceAll("\n", "<br>");
        }
    },
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : value;
    }
}
