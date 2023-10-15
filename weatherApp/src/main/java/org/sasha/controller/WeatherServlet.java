package org.sasha.controller;

import org.sasha.dto.WeatherDto.WeatherDto;

import org.sasha.service.WeatherService;
import org.sasha.service.impl.WeatherServiceImpl;
import org.sasha.utils.Formater;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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

    public static final String MOSCOW =
            "http://api.weatherapi.com/v1/forecast.json?key=cab6006614334d85a8f181853231310&q=moscow&days=3&aqi=no&alerts=no";

    /**
     * File for saving output data.
     */
    public static final String REPORT_FILE = "report.txt";
    private final WeatherService weatherService = new WeatherServiceImpl();

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
            throws IOException {

        WeatherDto weatherData = weatherService.getWetherData(MOSCOW);

        Formater formater = new Formater(weatherData);

        PrintWriter out = response.getWriter();
        out.write(formater.formatAllOutput());

        PrintWriter writer = new PrintWriter(REPORT_FILE);
        writer.write(formater.formatAllOutput());
        writer.close();

        File f = new File(REPORT_FILE);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(f);
    }
}
