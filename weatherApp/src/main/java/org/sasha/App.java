package org.sasha;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.sasha.dao.UserDao;
import org.sasha.dto.UserDto;
import org.sasha.controller.LoginServlet;
import org.sasha.controller.WeatherServlet;
import org.sasha.controller.UsersServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

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

        app.setBaseDir(System.getProperty("java.org.tmpdir"));
        app.setPort(port);

        //Context ctx = app.addContext("", new File(".").getAbsolutePath());
        Context ctx = app.addWebapp("", new File("src/main/resources/webapp").getAbsolutePath());

        app.addServlet(ctx, WeatherServlet.class.getSimpleName(), new WeatherServlet());
        ctx.addServletMappingDecoded("", WeatherServlet.class.getSimpleName());

        app.addServlet(ctx, LoginServlet.class.getSimpleName(), new LoginServlet());
        ctx.addServletMappingDecoded("/login", LoginServlet.class.getSimpleName());
        ctx.addServletMappingDecoded("/logout", LoginServlet.class.getSimpleName());

        app.addServlet(ctx, UsersServlet.class.getSimpleName(), new UsersServlet());
        ctx.addServletMappingDecoded("/users/*", UsersServlet.class.getSimpleName());

        return app;
    }

    public static String getFileContent(String fileName) throws IOException {
        Path pathToSolution = Paths.get(fileName).toAbsolutePath();
        return Files.readString(pathToSolution).trim();
    }

    public static void main(String[] args) throws LifecycleException, IOException, SQLException {

//        Connection connection = DBConfig.getConnection();
//        Statement statement = connection.createStatement();
//        String initSql = getFileContent("src/main/resources/init.sql");
//
//        statement.execute(initSql);

        Tomcat app = getApp(getPort());
        app.start();

        UserDao userDao = new UserDao();
        UserDto user = new UserDto("Ivan", "Novgorod", "1@1", "pass");
        userDao.save(user);

        app.getServer().await();
    }
}
