package org.sasha;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.sasha.config.DBConfig;
import org.sasha.controller.TownWeatherServlet;
import org.sasha.controller.WeatherServlet;
import org.sasha.dao.WeatherDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Configuring and launching the application
 */

public class App {

    /**
     * name environment variable
     */
    public static final String PORT = "PORT";

    /**
     * the port on which the application will be launched by default
     */
    public static final String DEFAULT_PORT = "8000";

    /**
     * Defines the port for launching the application.
     *
     * @return port number
     */
    private static int getPort() {
        String port = System.getenv(PORT);
        if (port != null) {
            return Integer.parseInt(port);
        }
        return Integer.parseInt(DEFAULT_PORT);
    }

    /**
     * Getting a Tomcat, registering servlets.
     *
     * @param port number
     * @return Tomcat type object
     */
    public static Tomcat getApp(int port) {
        Tomcat app = new Tomcat();

        app.setBaseDir(System.getProperty("java.io.tmpdir"));
        app.setPort(port);

        Context ctx = app.addContext("", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, WeatherServlet.class.getSimpleName(), new WeatherServlet());
        ctx.addServletMappingDecoded("/", WeatherServlet.class.getSimpleName());

        Tomcat.addServlet(ctx, TownWeatherServlet.class.getSimpleName(), new TownWeatherServlet());
        ctx.addServletMappingDecoded("/t", TownWeatherServlet.class.getSimpleName());

        return app;
    }

    public static String getFileContent(String fileName) throws IOException {
        Path pathToSolution = Paths.get(fileName).toAbsolutePath();
        return Files.readString(pathToSolution).trim();
    }

    public static void main(String[] args) throws LifecycleException, IOException, SQLException {
        Connection connection = DBConfig.getConnection();
        Statement statement = connection.createStatement();
        String initSql = getFileContent("src/main/resources/init.sql");

        statement.execute(initSql);

        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();
    }
}