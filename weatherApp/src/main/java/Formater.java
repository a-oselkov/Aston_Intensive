import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Formater {

    public static String formatRegionInfoOutput (WeatherData weatherData) {
         return "Region: " + weatherData.regionInfo;
    }
    public static String formatCountryInfoOutput (WeatherData weatherData) {
        return  "Country: " + weatherData.countryInfo;
    }
    public static String formatCurrentWeatherOutput (WeatherData weatherData) {
         return  "Current weather: " + weatherData.currentWeather;
    }

    public static String formatPerHourWeatherOutput (WeatherData weatherData) {
        StringBuilder out = new StringBuilder();
        out.append("Hourly weather forecast for ").append(LocalDate. now ()).append(":\n\n");
        for(Map.Entry<String, String> hourWeather : weatherData.perHourWeather.entrySet()) {
            out.append(hourWeather.getKey()).append(" - ")
                    .append(hourWeather.getValue()).append("\n");
        }
        return  out.toString();
    }

    public static String formatForThreeDayWeatherOutput (WeatherData weatherData) {
        StringBuilder out = new StringBuilder();
        out.append("Weather forecast for 3 days:\n\n");
        for(Map.Entry<String, Map<String, String>> weather : weatherData.weatherForThreeDays.entrySet()) {
            out.append(weather.getKey()).append("\n")
                    .append("Min: ").append(weather.getValue().get("min")).append("\n")
                    .append("Max: ").append(weather.getValue().get("max")).append("\n")
                    .append("Avg: ").append(weather.getValue().get("avg")).append("\n\n");

        }
        return  out.toString();
    }
}
