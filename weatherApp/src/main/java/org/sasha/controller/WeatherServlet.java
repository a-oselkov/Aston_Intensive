package org.sasha.controller;

import org.sasha.dto.CurrentWeatherCheckDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.CurrentWeatherCheckService;
import org.sasha.service.LocationService;
import org.sasha.service.UserService;
import org.sasha.service.WeatherService;
import org.sasha.service.impl.CurrentWeatherCheckServiceImpl;
import org.sasha.service.impl.LocationServiceImpl;
import org.sasha.service.impl.UserServiceImpl;
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
    private final LocationService locationService = new LocationServiceImpl();
    private final CurrentWeatherCheckService currentWeatherCheckService = new CurrentWeatherCheckServiceImpl();
    private final UserService userService = new UserServiceImpl();

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
        WeatherDto weatherData1 = weatherService.getWetherData(apiUrl);
        WeatherDto weatherData2 = weatherService.getWetherData(getApiUrl(userService.findById(ID).get().getRegion()));

        LocationDto locationDto = weatherService.getWetherData(apiUrl).getLocation();
        CurrentDto current = weatherService.getWetherData(apiUrl).getCurrent();

        long locationId = locationService.save(locationDto);

        CurrentWeatherCheckDto checkDto = new CurrentWeatherCheckDto(current.getTemp_c(), current.getFeelslike_c(),
                current.getCloud(), ID, locationId);
        currentWeatherCheckService.save(checkDto);

        session.setAttribute("weather1", weatherData1);
        session.setAttribute("weather2", weatherData2);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/start.jsp");
        response.setStatus(200);
        requestDispatcher.forward(request, response);
    }
}
