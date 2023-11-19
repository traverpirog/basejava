package com.javaops.webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String title;
    private String description;

    public Period() {
    }

    public Period(LocalDate dateFrom, LocalDate dateTo, String title, String description) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.title = title;
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!dateFrom.equals(period.dateFrom)) return false;
        if (!dateTo.equals(period.dateTo)) return false;
        if (!title.equals(period.title)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = dateFrom.hashCode();
        result = 31 * result + dateTo.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
