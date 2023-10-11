import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class Writer extends HttpServlet {
    String file;
    WeatherData weatherData;

    public Writer(WeatherData weatherData, String file) {
        this.file = file;
        this.weatherData = weatherData;
    }

    public void writeWeatherDataInFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.write(Formater.formatRegionInfoOutput(weatherData));
        writer.write("\n" + Formater.formatCountryInfoOutput(weatherData));
        writer.write("\n\n" + Formater.formatCurrentWeatherOutput(weatherData));
        writer.write("\n\n" + Formater.formatPerHourWeatherOutput(weatherData));
        writer.write("\n\n" + Formater.formatForThreeDayWeatherOutput(weatherData));
        writer.flush();
        writer.close();
    }

    public void writeWeatherDataOnScreen (HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(Formater.formatRegionInfoOutput(weatherData));
        out.println(Formater.formatCountryInfoOutput(weatherData));
        out.println("\n" + Formater.formatCurrentWeatherOutput(weatherData));
        out.println("\n" + Formater.formatPerHourWeatherOutput(weatherData));
        out.println("\n" + Formater.formatForThreeDayWeatherOutput(weatherData));
        out.flush();
        out.close();
    }

    public void openFileAfterWrite() throws IOException {
        File f = new File(file);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(f);
    }
}
