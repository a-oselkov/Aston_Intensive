<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tag:application>
    <h1>Данные пользователя</h1>
    <div class="card">
        <ul class="list-group list-group-flush">
            <li class="list-group-item">id: ${user.getId()}</li>
            <li class="list-group-item">Регион : ${user.getRegion()}</li>
            <li class="list-group-item">Email: ${user.getEmail()}</li>
        </ul>
        <div class="card-footer">
            <form action='/users/edit?id=${user.getId()}' method="get" style="display: inline >
                                <button type="submit" class="btn btn-primary">Редактировать</button>
            </form>
            <form action='/users/delete?id=${user.getId()}' method="post" style="display: inline">
                    <button type="submit" class="btn btn-danger">Удалить</button>
            </form>
        </div>
    </div>
    <br>
    <div class="card">
    <table class="table">
            <thead>
                <th>ID</th>
                <th>Регион</th>
                <th>Температура</th>
                <th>Ощущается как</th>
                <th>Облачность</th>
                <th>Время проверки</th>
            </thead>
            <tbody>
            <c:forEach var="check" items="${checks}">
                <tr>
                    <td>${check.getId()}</td>
                    <td>${check.getLocation().getRegion()}</td>
                    <td>${check.getTemp_c()}</td>
                    <td>${check.getFeelsLike_c()}</td>
                    <td>${check.getCloud()}</td>
                    <td>${check.getCreatedAt()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</tag:application>
