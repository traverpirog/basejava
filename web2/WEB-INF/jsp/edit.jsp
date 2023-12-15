<%@ page import="com.javaops.webapp.model.ContactType" %>
<%@ page import="com.javaops.webapp.model.SectionType" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="resume" type="com.javaops.webapp.model.Resume" scope="request"/>
<jsp:include page="/WEB-INF/fragments/header.jsp">
    <jsp:param name="title" value="Редактирование резюме"/>
</jsp:include>
<main>
    <section class="container">
        <a class="button" href="resume">Все резюме</a>
        <button class="button button--border" onclick="window.history.back()">Назад</button>
        <% if (request.getParameter("action").equals("create")) { %>
        <h1>Создание резюме</h1>
        <% } else { %>
        <h1>Редактирование резюме</h1>
        <% } %>
        <form class="form" action="resume?action=<%= request.getParameter("action") %>" method="post">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <label class="form-label">
                <span class="form-label__caption">Имя:</span>
                <input type="text" name="fullName" class="form-input" value="${resume.fullName}">
            </label>
            <hr>
            <h2>Контакты:</h2>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <label class="form-label">
                    <span class="form-label__caption">${type.title}:</span>
                    <input type="text" name="${type.name()}" class="form-input" value="${resume.getContact(type)}">
                </label>
            </c:forEach>
            <hr>
            <h2>Информация:</h2>
            <c:forEach var="type" items="<%=SectionType.values()%>">
                <label class="form-label">
                    <span class="form-label__caption">${type.title}:</span>
                    <textarea name="${type.name()}" class="form-input form-input--textarea">${resume.getSection(type)}</textarea>
                </label>
            </c:forEach>
            <button class="button" type="submit">Сохранить</button>
            <button class="button button--border" type="button" onclick="window.history.back()">Назад</button>
        </form>
    </section>
</main>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>