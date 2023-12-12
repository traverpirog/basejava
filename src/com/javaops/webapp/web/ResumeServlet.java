package com.javaops.webapp.web;

import com.javaops.webapp.Config;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        response.getWriter().write(generateHtmlTable(uuid));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String generateHtmlTable(String uuid) {
        StringBuilder html = new StringBuilder();
        html
                .append("<link rel=\"stylesheet\" href=\"css/styles.css\">")
                .append("<table>")
                .append("<thead>")
                .append("<tr>")
                .append("<th>").append("UUID")
                .append("</th>")
                .append("<th>").append("FULL_NAME")
                .append("</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            generateTableRow(html, resume);
        } else {
            for (Resume resume : storage.getAllSorted()) {
                generateTableRow(html, resume);
            }
        }
        html.append("</tbody>")
                .append("</table>");
        return String.valueOf(html);
    }

    private void generateTableRow(StringBuilder html, Resume resume) {
        html.append("<tr>")
                .append("<td>")
                .append(resume.getUuid())
                .append("</td>")
                .append("<td>")
                .append(resume.getFullName())
                .append("</td>")
                .append("</tr>");
    }
}
