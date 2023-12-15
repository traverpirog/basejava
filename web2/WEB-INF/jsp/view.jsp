<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:useBean id="resume" type="com.javaops.webapp.model.Resume" scope="request"/>
<jsp:include page="/WEB-INF/fragments/header.jsp">
    <jsp:param name="title" value="Резюме - ${resume.fullName}"/>
</jsp:include>
<main>
    <section class="container">
        <a class="button button--border" href="resume">Все резюме</a>
        <a class="button" href="resume?uuid=${resume.uuid}&action=edit">Редактировать</a>
        <a class="button" href="resume?uuid=${resume.uuid}&action=delete" style="background-color: red">Удалить</a>
        <h1>${resume.fullName}</h1>
        <hr>
        <div class="accordion">
            <c:if test="${not empty resume.contacts}">
                <div class="accordion-item">
                    <button class="accordion-item__handler accordion-item__handler--active">
                        Контакты
                        <svg width="16" height="16" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                  d="M35.4743 4.36162C35.4743 2.44273 33.9189 0.887268 32 0.887268C30.0812 0.887268 28.5257 2.44273 28.5256 4.36162V28.5256H4.36163C2.44282 28.5256 0.887268 30.0812 0.887268 32C0.887268 33.9188 2.44282 35.4743 4.36163 35.4743H28.5256V59.6383C28.5257 61.5572 30.0812 63.1127 32 63.1127C33.9189 63.1127 35.4743 61.5571 35.4743 59.6383L35.4744 35.4743H59.6383C61.5572 35.4743 63.1127 33.9188 63.1127 32C63.1127 30.0811 61.5572 28.5256 59.6383 28.5256H35.4744L35.4743 4.36162Z"></path>
                        </svg>
                    </button>
                    <div class="accordion-item__content accordion-item__content--active">
                        <ul>
                            <c:forEach var="contactEntry" items="${resume.contacts}">
                                <jsp:useBean id="contactEntry"
                                             type="java.util.Map.Entry<com.javaops.webapp.model.ContactType, java.lang.String>"/>
                                <li>
                                    <%= contactEntry.getKey().toHtml(contactEntry.getValue()) %>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty resume.sections}">
                <div class="accordion-item">
                    <button class="accordion-item__handler">
                        Информация
                        <svg width="16" height="16" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                  d="M35.4743 4.36162C35.4743 2.44273 33.9189 0.887268 32 0.887268C30.0812 0.887268 28.5257 2.44273 28.5256 4.36162V28.5256H4.36163C2.44282 28.5256 0.887268 30.0812 0.887268 32C0.887268 33.9188 2.44282 35.4743 4.36163 35.4743H28.5256V59.6383C28.5257 61.5572 30.0812 63.1127 32 63.1127C33.9189 63.1127 35.4743 61.5571 35.4743 59.6383L35.4744 35.4743H59.6383C61.5572 35.4743 63.1127 33.9188 63.1127 32C63.1127 30.0811 61.5572 28.5256 59.6383 28.5256H35.4744L35.4743 4.36162Z"></path>
                        </svg>
                    </button>
                    <div class="accordion-item__content">
                        <c:forEach var="sectionEntry" items="${resume.sections}">
                            <jsp:useBean id="sectionEntry"
                                         type="java.util.Map.Entry<com.javaops.webapp.model.SectionType, com.javaops.webapp.model.AbstractSection>"/>
                            <div>
                                <h3><%= sectionEntry.getKey().getTitle() %>
                                </h3>
                                <p><%= sectionEntry.getValue() %>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
    </section>
</main>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>