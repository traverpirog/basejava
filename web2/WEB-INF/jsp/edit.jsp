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
        <form class="form" action="resume?action=<%=request.getParameter("action")%>" method="post">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <label class="form-label">
                <span class="form-label__caption">Имя:</span>
                <input type="text" name="fullName" class="form-input" value="${resume.fullName}" required>
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
                <c:choose>
                    <c:when test="${type.name() == SectionType.EXPERIENCE || type.name() == SectionType.EDUCATION}">
                        <div class="form-label">
                            <h4 class="form-label__caption">${type.title}:</h4>
                            <c:set var="section" value="${resume.getSection(type)}"/>
                            <c:choose>
                                <c:when test="${not empty section}">
                                    <jsp:useBean id="section" type="com.javaops.webapp.model.CompanySection"/>
                                    <c:set var="positionCompany" value="0"/>
                                    <div class="form-label__list form-label__list--company">
                                        <c:forEach var="company" items="${section.companies}">
                                            <jsp:useBean id="company" type="com.javaops.webapp.model.Company"/>
                                            <div class="form-label form-label--company">
                                                <input type="text" class="form-input"
                                                       name="${type.name()}${positionCompany}name"
                                                       value="${company.name}"
                                                       placeholder="Название компании" required/>
                                                <input type="text" class="form-input"
                                                       name="${type.name()}${positionCompany}"
                                                       value="${company.website}website"
                                                       placeholder="Ссылка на сайт"/>
                                                <div class="form-label__list form-label__list--period">
                                                    <c:forEach var="period" items="${company.periods}">
                                                        <div class="form-period">
                                                            <jsp:useBean id="period"
                                                                         type="com.javaops.webapp.model.Period"/>
                                                            <input type="date" class="form-input"
                                                                   name="${type.name()}${positionCompany}dateFrom"
                                                                   value="${period.dateFrom}"
                                                                   placeholder="Начало, ДД.ММ.ГГГГ" required/>
                                                            <input type="date" class="form-input"
                                                                   name="${type.name()}${positionCompany}dateTo"
                                                                   value="${period.dateTo}"
                                                                   placeholder="Конец, ДД.ММ.ГГГГ" required/>
                                                            <input type="text" class="form-input"
                                                                   name="${type.name()}${positionCompany}title"
                                                                   value="${period.title}" placeholder="Заголовок"
                                                                   required/>
                                                            <textarea
                                                                    name="${type.name()}${positionCompany}description"
                                                                    class="form-input form-input--textarea">${period.description}</textarea>
                                                        </div>
                                                    </c:forEach>
                                                    <button type="button" class="button button--border add-period-js"
                                                            data-company-id="${positionCompany}"
                                                            data-type="${type.name()}">
                                                        Добавить период
                                                    </button>
                                                </div>
                                            </div>
                                            <hr style="margin: 48px 0">
                                            <c:set var="positionCompany" value="${positionCompany + 1}"/>
                                        </c:forEach>
                                        <button type="button" class="button add-company-js"
                                                data-company-id="${positionCompany}"
                                                data-period-id="${positionPeriod}" data-type="${type.name()}">Добавить
                                            компанию
                                        </button>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-label__list form-label__list--company">
                                        <div class="form-label form-label--company">
                                            <input type="text" class="form-input"
                                                   name="${type.name()}[]" value="${company.name}"
                                                   placeholder="Название компании" />
                                            <input type="text" class="form-input"
                                                   name="${type.name()}[]"
                                                   value="${company.website}"
                                                   placeholder="Ссылка на сайт"/>
                                            <div class="form-label__list form-label__list--period">
                                                <div class="form-period">
                                                    <input type="date" class="form-input"
                                                           name="${type.name()}[]"
                                                           value="${period.dateFrom}"
                                                           placeholder="Начало, ДД.ММ.ГГГГ" />
                                                    <input type="date" class="form-input"
                                                           name="${type.name()}[]"
                                                           value="${period.dateTo}"
                                                           placeholder="Конец, ДД.ММ.ГГГГ" />
                                                    <input type="text" class="form-input"
                                                           name="${type.name()}[]"
                                                           value="${period.title}" placeholder="Заголовок"
                                                           />
                                                    <textarea
                                                            name="${type.name()}[]"
                                                            class="form-input form-input--textarea">${period.description}</textarea>
                                                    <c:set var="positionPeriod" value="${positionPeriod + 1}"/>
                                                </div>
                                                <button type="button" class="button button--border add-period-js"
                                                        data-period-id="${positionPeriod}"
                                                        data-company-id="${positionCompany}" data-type="${type.name()}">
                                                    Добавить период
                                                </button>
                                            </div>
                                        </div>
                                        <hr style="margin: 48px 0">
                                        <c:set var="positionCompany" value="${positionCompany + 1}"/>
                                        <button type="button" class="button add-company-js"
                                                data-company-id="${positionCompany}"
                                                data-period-id="${positionPeriod}" data-type="${type.name()}">Добавить
                                            компанию
                                        </button>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-label">
                            <h4 class="form-label__caption">${type.title}:</h4>
                            <textarea name="${type.name()}"
                                      class="form-input form-input--textarea">${resume.getSection(type)}</textarea>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <button class="button" type="submit">Сохранить</button>
            <button class="button button--border" type="button" onclick="window.history.back()">Назад</button>
        </form>
    </section>
</main>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>