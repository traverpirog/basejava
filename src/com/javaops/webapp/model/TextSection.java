package com.javaops.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
