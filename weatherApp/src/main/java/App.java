import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

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

        return app;
    }

    public static void main(String[] args) throws LifecycleException, IOException {
        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();
    }
}