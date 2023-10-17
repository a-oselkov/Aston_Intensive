package org.sasha.controller;

import org.sasha.Model.TownWeather;
import org.sasha.service.TownWeatherService;
import org.sasha.service.impl.TownWeatherServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MinMaxTempServlet extends HttpServlet {

    private final TownWeatherService townWeatherService = new TownWeatherServiceImpl();
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //townWeatherService.deleteAll();
        List<TownWeather> towns;
        try {
            towns = townWeatherService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(towns);
        PrintWriter out = response.getWriter();
        out.write("MAX TEMP: " + towns.get(towns.size() - 1).getName() + " " +
                towns.get(towns.size() - 1).getTemp());
        out.write("\n\nMIN TEMP: " + towns.get(0).getName() + " " +
                towns.get(0).getTemp());
    }
}
