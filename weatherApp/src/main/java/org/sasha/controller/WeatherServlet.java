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
import org.sasha.utils.Formater;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.sasha.utils.Request.getApiUrl;

/**
 * Servlet class.
 * Processes a GET request to endpoint "/".
 * Sends a request to a service that provides weather information.
 * Processes and generates a response that contains the current weather,
 * hourly forecast and forecast for three days.
 * Displays information on the screen and writes it to a file.
 * Contains the constants required for the request,
 * and the constant - the name of the file where the response will be written.
 */

public class WeatherServlet extends HttpServlet {
    /**
     * File for saving output data.
     */
    public static final String REPORT_FILE = "report.txt";
    private final WeatherService weatherService = new WeatherServiceImpl();
    private final TownWeatherService townWeatherService = new TownWeatherServiceImpl();
    private final LocationService locationService = new LocationServiseImpl();
    private final CurrentWeatherService currentWeatherService = new CurrentWeatherServiceImpl();

    /**
     * Overriding a method from the HttpServlet class.
     *
     * @param request  object that contains the request the client has made
     *                 of the servlet
     * @param response object that contains the response the servlet sends
     *                 to the client
     * @throws IOException if an input or output error is detected when the servlet handles
     *                     the GET request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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

        Formater formater = new Formater(weatherData);

        PrintWriter out = response.getWriter();
        out.write(formater.formatAllOutput());

        PrintWriter writer = new PrintWriter(REPORT_FILE);
        writer.write(formater.formatAllOutput());
        writer.close();

//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view.jsp");
//        requestDispatcher.forward(request, response);
//



//        File f = new File(REPORT_FILE);
//        Desktop desktop = Desktop.getDesktop();
//        desktop.open(f);
//        String t = "eete";
//        session.setAttribute("w", weatherData);
//        request.getRequestDispatcher("/index.html").forward(request, response);


    }
}
