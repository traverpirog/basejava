package com.javaops.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> content;

    public ListSection(List<String> content) {
        this.content = content;
    }

    public List<String> getList() {
        return content;
    }

    public void setList(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "content=" + content +
                '}';
    }
}
