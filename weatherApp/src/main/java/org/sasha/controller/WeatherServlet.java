package org.sasha.controller;

import org.sasha.dto.CurrentWeatherCheckDto;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.model.Location;
import org.sasha.service.CurrentWeatherCheckService;
import org.sasha.service.LocationService;
import org.sasha.service.TownWeatherService;
import org.sasha.service.WeatherService;
import org.sasha.service.impl.CurrentWeatherCheckServiceImpl;
import org.sasha.service.impl.LocationServiseImpl;
import org.sasha.service.impl.TownWeatherServiceImpl;
import org.sasha.service.impl.WeatherServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.sasha.controller.LoginServlet.ID;
import static org.sasha.utils.Request.getApiUrl;

public class WeatherServlet extends HttpServlet {

    private final WeatherService weatherService = new WeatherServiceImpl();
    private final TownWeatherService townWeatherService = new TownWeatherServiceImpl();
    private final LocationService locationService = new LocationServiseImpl();
    private final CurrentWeatherCheckService currentWeatherCheckService = new CurrentWeatherCheckServiceImpl();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String parameter = request.getParameter("town");

        if (parameter == null || parameter.isEmpty()) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
            session.setAttribute("flash", "Введите город для проверки погоды");
            response.setStatus(422);
            requestDispatcher.forward(request, response);
            return;
        }

        String apiUrl = getApiUrl(parameter);
        WeatherDto weatherData = weatherService.getWetherData(apiUrl);


        TownWeatherDto townWeather = townWeatherService.getTownWeatherData(apiUrl);
        LocationDto locationDto = weatherService.getWetherData(apiUrl).getLocation();
        CurrentDto current = weatherService.getWetherData(apiUrl).getCurrent();


        townWeatherService.save(townWeather);
        locationService.save(locationDto);

        Location location = locationService.findByRegion(locationDto.getRegion()).get();

        CurrentWeatherCheckDto checkDto = new CurrentWeatherCheckDto(current.getTemp_c(), current.getFeelslike_c(),
                current.getCloud(), ID, location.getId());
        currentWeatherCheckService.save(checkDto);

        session.setAttribute("weather", weatherData);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
        requestDispatcher.forward(request, response);
    }
}
