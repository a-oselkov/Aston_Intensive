import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 * A class with methods for writing the received weather information
 * to a file and displaying the information on the screen.
 */
public final class WeatherWriter {

    /**
     * The name of the file to use as the destination of this writer.
     */
    private final String file;

    /**
     * an object containing weather information
     */
    private final WeatherData weatherData;

    /**
     * A class with methods for writing the received weather information
     * to a file and displaying the information on the screen.
     * @param weatherData an object containing weather information
     * @param file name of the file to use as the destination of this writer
     */
    public WeatherWriter(WeatherData weatherData, String file) {
        this.weatherData = weatherData;
        this.file = file;
    }

    /**
     * Writing information from an object weatherData to a file
     * to a file and displaying the information on the screen.
     * @throws FileNotFoundException If the given string does not denote an existing,
     *                               writable regular file and a new regular file of that name cannot be created,
     *                               or if some other error occurs while opening or creating the file
     */
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

    /**
     * Displaying weather information on the screen.
     * @param response HttpServletResponse object
     * @throws FileNotFoundException If the given string does not denote an existing,
     *                               writable regular file and a new regular file of that name cannot be created,
     *                               or if some other error occurs while opening or creating the file
     */
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

    /**
     * Opens the file where the weather information was recorded.
     * @throws IOException Signals that an I/O exception of some sort has occurred
     */
    public void openFileAfterWrite() throws IOException {
        File f = new File(file);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(f);
    }
}
