import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
    String file;
    WeatherData weatherData;
    PrintWriter writer;

    public Writer(WeatherData weatherData, String file) {
        this.file = file;
        this.weatherData = weatherData;
    }

    public void writeWeatherData() throws FileNotFoundException {
        writer = new PrintWriter(file);
        writer.write(Formater.formatRegionInfoOutput(weatherData));
        writer.write("\n" + Formater.formatCountryInfoOutput(weatherData));
        writer.write("\n\n" + Formater.formatCurrentWeatherOutput(weatherData));
        writer.write("\n\n" + Formater.formatPerHourWeatherOutput(weatherData));
        writer.write("\n\n" + Formater.formatForThreeDayWeatherOutput(weatherData));
        writer.close();
    }

    public void openAfterWrite() throws IOException {
        File f = new File(file);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(f);
    }
}
