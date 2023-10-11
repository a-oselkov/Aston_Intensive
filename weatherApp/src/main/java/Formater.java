import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Contains methods for generating the output of the received weather information.
 */
public final class Formater {

    /**
     * Generates a string that contains information about the region for which the weather is determined.
     * @param weatherData An object containing weather data
     * @return a line with information about the region
     */
    public static String formatRegionInfoOutput (WeatherData weatherData) {
         return "Region: " + weatherData.getRegionInfo();
    }

    /**
     * Generates a string that contains information about the country for which the weather is determined.
     * @param weatherData An object containing weather data
     * @return a line with information about the country
     */
    public static String formatCountryInfoOutput (WeatherData weatherData) {
        return  "Country: " + weatherData.getCountryInfo();
    }

    /**
     * Generates a string that contains information about the current weather.
     * @param weatherData An object containing weather data
     * @return a line with information about the current weather
     */
    public static String formatCurrentWeatherOutput (WeatherData weatherData) {
         return  "Current weather: " + weatherData.getCurrentWeather();
    }

    /**
     * Generates a string that contains information with hourly weather forecast.
     * @param weatherData An object containing weather data
     * @return a line with information with hourly weather forecast
     */
    public static String formatPerHourWeatherOutput (WeatherData weatherData) {
        StringBuilder out = new StringBuilder();
        out.append("Hourly weather forecast for ").append(LocalDate. now ()).append(":\n\n");
        for(Map.Entry<String, String> hourWeather : weatherData.getPerHourWeather().entrySet()) {
            out.append(hourWeather.getKey()).append(" - ")
                    .append(hourWeather.getValue()).append("\n");
        }
        return  out.toString();
    }

    /**
     * Generates a string that contains information with a forecast for 3 days.
     * @param weatherData An object containing weather data
     * @return a line with information with a forecast for 3 days
     */
    public static String formatForThreeDayWeatherOutput (WeatherData weatherData) {
        StringBuilder out = new StringBuilder();
        out.append("Weather forecast for 3 days:\n\n");
        for(Map.Entry<String, Map<String, String>> weather : weatherData.getWeatherForThreeDays().entrySet()) {
            out.append(weather.getKey()).append("\n")
                    .append("Min: ").append(weather.getValue().get("min")).append("\n")
                    .append("Max: ").append(weather.getValue().get("max")).append("\n")
                    .append("Avg: ").append(weather.getValue().get("avg")).append("\n\n");
        }
        return  out.toString();
    }
}
