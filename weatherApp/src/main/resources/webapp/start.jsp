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
                <input class="form-control" type="text" name="town" required>
                <br>
                <button class="btn btn-primary" type="submit">Проверить погоду</button>
            </form>
        </div>
    </div>
    <br>
    <p> Погода в своем регионе: </p>
    <div class="card">
            <div class="card-body">
                <p>Регион: ${weather2.getLocation().getRegion()}</p>
                <p>Сегодня: ${weather2.getLocation().getLocaltime()}</p>
                <p>Погода сейчас: ${weather2.getCurrent().getTemp_c()}</p>
            </div>
        </div>
    <br>
    <p> Последняя проверка: </p>
    <div class="card">
            <div class="card-body">
                <p>Регион: ${weather1.getLocation().getRegion()}</p>
                <p>Сегодня: ${weather1.getLocation().getLocaltime()}</p>
                <p>Погода сейчас: ${weather1.getCurrent().getTemp_c()}</p>
            </div>
        </div>
</tag:application>
