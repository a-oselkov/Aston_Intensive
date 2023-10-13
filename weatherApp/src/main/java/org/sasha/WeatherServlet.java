package org.sasha;

import org.sasha.Model.CurrentWeather;
import org.sasha.utils.Formater;
import org.sasha.utils.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


import static org.sasha.utils.Parser.getCurrentWeather;


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
     * The address of the third-party service to which the request for weather information will be sent.
     */
    public static final String WEATHER_API_URL =
            "https://weatherapi-com.p.rapidapi.com/forecast.json?q=58.3130%2C31.1630%20&days=3";

    /**
     * Request header name.
     */
    public static final String HEADER_NAME = "X-RapidAPI-Key";

    /**
     * Request header value
     */
    public static final String HEADER_VALUE = "85693fc932mshb3d33fad86f58b7p1cc6bcjsn164c661c6234";

    /**
     * File for saving output data.
     */
    public static final String REPORT_FILE = "report.txt";

    /**
     * Overriding a method from the HttpServlet class.
     * @param request object that contains the request the client has made
     *                of the servlet
     * @param response object that contains the response the servlet sends
     *                 to the client
     * @throws IOException if an input or output error is detected when the servlet handles
     *          the GET request
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String dataFromApi = Request.get(WEATHER_API_URL, HEADER_NAME, HEADER_VALUE).getBody();
        
        CurrentWeather currentWeather = getCurrentWeather(dataFromApi);
        Formater formater = new Formater(currentWeather, dataFromApi, 3, 24);

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