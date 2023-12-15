package com.javaops.webapp.web;

import com.javaops.webapp.Config;
import com.javaops.webapp.model.*;
import com.javaops.webapp.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        StringBuilder requestPath = new StringBuilder("/WEB-INF/jsp/");
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                requestPath.append(action).append(".jsp");
                break;
            case "create":
                resume = new Resume("");
                requestPath.append("edit").append(".jsp");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(requestPath.toString()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (fullName != null && !fullName.trim().isEmpty()) {
            if (action.equals("create")) {
                resume = new Resume(uuid, fullName);
            } else {
                resume = storage.get(uuid);
                resume.setFullName(fullName);
            }
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && !value.trim().isEmpty()) {
                    resume.addContact(type, value.trim());
                } else {
                    resume.getContacts().remove(type);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && !value.trim().isEmpty()) {
                    switch (type) {
                        case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value.trim()));
                        case ACHIEVEMENT, QUALIFICATIONS ->
                                resume.addSection(type, new ListSection(Arrays.stream(value.trim().split("\n")).toList()));
                    }
                } else {
                    resume.getSections().remove(type);
                }
            }
            switch (action) {
                case "create" -> {
                    storage.save(resume);
                    response.sendRedirect("resume?uuid=" + resume.getUuid() + "&action=edit");
                }
                case "update" -> {
                    storage.update(resume);
                    response.sendRedirect("resume");
                }
            }
        } else {
            response.sendRedirect("resume");
        }
    }
}
