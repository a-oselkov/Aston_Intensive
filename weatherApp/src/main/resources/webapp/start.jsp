<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:application>
    <div class="card">
        <div class="card-body">
           WeatherAPI
        </div>
    </div>
    <br>
    <div class="card">
        <div class="card-body">
            <form action="">
                <label>Введите город</label>
                <input class="form-control" type="text" name="town">
                <br>
                <button class="btn btn-primary" type="submit">Проверить погоду</button>
            </form>
        </div>
    </div>
    <br>
    <div class="card">
        <div class="card-body">
            <p>Регион: ${weather.getLocation().getRegion()}</p>
            <p>Сегодня: ${weather.getLocation().getLocaltime()}</p>
            <p>Погода сейчас: ${weather.getCurrent().getTemp_c()}</p>
        </div>
    </div>
    <br>
    <div class="card">
        <div class="card-body">
            <p>Регион: ${weather.getLocation().getRegion()}</p>
            <p>Сегодня: ${weather.getLocation().getLocaltime()}</p>
            <p>Погода сейчас: ${weather.getCurrent().getTemp_c()}</p>
        </div>
    </div>
</tag:application>
