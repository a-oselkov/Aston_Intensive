import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import java.io.File;
import java.io.IOException;

public class Main {
private static int getPort() {
    String port = System.getenv("PORT");
    if (port != null) {
        return Integer.valueOf(port);
    }
    return 8000;
}

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