<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tag:application>
    <table class="table">
        <thead>
            <th>ID</th>
            <th>Имя</th>
            <th>Регион</th>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.getId()}</td>
                <td><a href='/users/show?id=${user.getId()}'>${user.getName()}</a></td>
                <td>${user.getRegion()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</tag:application>
