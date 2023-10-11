import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class WeatherServlet extends HttpServlet {
    public static final String WEATHER_API_URL =
            "https://weatherapi-com.p.rapidapi.com/forecast.json?q=58.3130%2C31.1630%20&days=3";
    public static final String HEADER_NAME = "X-RapidAPI-Key";
    public static final String HEADER_VALUE = "85693fc932mshb3d33fad86f58b7p1cc6bcjsn164c661c6234";
    public static final String FILE_FOR_REPORT = "report.txt";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String dataFromApi = Request.get(WEATHER_API_URL, HEADER_NAME, HEADER_VALUE).getBody();
        WeatherDataHandler weatherDataHandler = new WeatherDataHandler(dataFromApi);

        WeatherData weatherData = new WeatherData();
        weatherData.regionInfo = weatherDataHandler.handleRegionInfo();
        weatherData.countryInfo = weatherDataHandler.handleCountryInfo();
        weatherData.currentWeather = weatherDataHandler.handleCurrentWeatherData();
        weatherData.perHourWeather = weatherDataHandler.handlePerHourWeather();
        weatherData.weatherForThreeDays = weatherDataHandler.handleWeatherForThreeDays();

        PrintWriter out = response.getWriter();
        out.println(Formater.formatRegionInfoOutput(weatherData));
        out.println(Formater.formatCountryInfoOutput(weatherData));
        out.println("\n" + Formater.formatCurrentWeatherOutput(weatherData));
        out.println("\n" + Formater.formatPerHourWeatherOutput(weatherData));
        out.println("\n" + Formater.formatForThreeDayWeatherOutput(weatherData));

        Writer writer = new Writer(weatherData, FILE_FOR_REPORT);
        writer.writeWeatherData();
        writer.openAfterWrite();
    }
}
