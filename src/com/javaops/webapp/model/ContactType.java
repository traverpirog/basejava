package com.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml(String value) {
            return "<a href='skype:" + value + "' >" + value + "</a>";
        }
    },
    EMAIL("Email") {
        @Override
        public String toHtml(String value) {
            return "<a href='mailto:" + value + "' >" + value + "</a>";
        }
    },
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverFlow"),
    WEBSITE("Website");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : title + ": " + value;
    }
}
