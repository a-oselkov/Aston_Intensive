<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:application>
    <h1>Регистрация</h1>
    <form action="/users/new" method="post">
        <div class="mb-3">
            <label>Имя</label>
            <input class="form-control" type="text" name="name" value='${user.getName()}'>
        </div>
        <div class="mb-3">
            <label>Регион</label>
            <input class="form-control" type="text" name="region" value='${user.getRegion()}'>
        </div>
        <div class="mb-3">
            <label>Email</label>
            <input class="form-control" type="email" name="email" value='${user.getEmail()}'>
        </div>
        <div class="mb-3">
                    <label>Пароль</label>
                    <input class="form-control" type="password" name="pass" value='${user.getPass()}'>
                </div>
        <button class="btn btn-primary" type="submit">Создать</button>
    </form>
</tag:application>
