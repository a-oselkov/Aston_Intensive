package org.sasha.controller;

import org.sasha.dto.TownWeatherDto;
import org.sasha.service.TownWeatherService;
import org.sasha.service.impl.TownWeatherServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.sasha.controller.WeatherServlet.MOSCOW;

public class TownWeatherServlet extends HttpServlet {

    private final TownWeatherService townWeatherService = new TownWeatherServiceImpl();

    public final String PETERBURG =
            "http://api.weatherapi.com/v1/forecast.json?key=cab6006614334d85a8f181853231310&q=peterburg&days=3&aqi=no&alerts=no";
    public final String PSKOV =
            "http://api.weatherapi.com/v1/forecast.json?key=cab6006614334d85a8f181853231310&q=pskov&days=3&aqi=no&alerts=no";
    public final String KALUGA =
            "http://api.weatherapi.com/v1/forecast.json?key=cab6006614334d85a8f181853231310&q=kaluga&days=3&aqi=no&alerts=no";
    public final String TVER =
            "http://api.weatherapi.com/v1/forecast.json?key=cab6006614334d85a8f181853231310&q=tver&days=3&aqi=no&alerts=no";

    private final List<String> towns = List.of(MOSCOW, PETERBURG, TVER, KALUGA, PSKOV);


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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {


        for (String town : towns) {
            TownWeatherDto townWeather = townWeatherService.getTownWeatherData(town);
            townWeatherService.save(townWeather);
        }

        try {
            System.out.println(townWeatherService.findById(1L));
            townWeatherService.deleteById(1L);
            System.out.println(townWeatherService.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
