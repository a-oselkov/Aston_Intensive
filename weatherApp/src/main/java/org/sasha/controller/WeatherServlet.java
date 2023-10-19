package org.sasha.controller;

import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.CurrentWeatherService;
import org.sasha.service.LocationService;
import org.sasha.service.TownWeatherService;
import org.sasha.service.WeatherService;
import org.sasha.service.impl.CurrentWeatherServiceImpl;
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

import static org.sasha.utils.Request.getApiUrl;

public class WeatherServlet extends HttpServlet {

    private final WeatherService weatherService = new WeatherServiceImpl();
    private final TownWeatherService townWeatherService = new TownWeatherServiceImpl();
    private final LocationService locationService = new LocationServiseImpl();
    private final CurrentWeatherService currentWeatherService = new CurrentWeatherServiceImpl();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String parameter = request.getParameter("town");

        String apiUrl = getApiUrl(parameter);
        WeatherDto weatherData = weatherService.getWetherData(apiUrl);

        TownWeatherDto townWeather = townWeatherService.getTownWeatherData(apiUrl);
        LocationDto location = weatherService.getWetherData(apiUrl).getLocation();
        CurrentDto current = weatherService.getWetherData(apiUrl).getCurrent();

        townWeatherService.save(townWeather);
        locationService.save(location);
        currentWeatherService.save(current, location);

        session.setAttribute("weather", weatherData);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
        requestDispatcher.forward(request, response);
    }
}
