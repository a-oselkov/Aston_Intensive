import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class for storing processed information received from a request to a third-party service.
 */
public final class WeatherData {
    /**
     * Information about the region.
     */
    private String regionInfo;

    /**
     * Information about the country.
     */
    private String countryInfo;

    /**
     * Information about the current weather.
     */
    private String currentWeather;

    /**
     * Information with hourly weather forecast.
     */
    private Map<String, String> perHourWeather = new LinkedHashMap<>();

    /**
     * Information with weather forecast for 3 days.
     */
    private Map<String, Map<String, String>> weatherForThreeDays = new LinkedHashMap<>();

    /**
     * Filling in the properties of an object of the WeatherData type.
     * @param weatherDataParser type object WeatherDataParser
     *                          to process information to fill in the properties of an object
     *                          WeatherData
     */
    public void fillWeatherData(WeatherDataParser weatherDataParser) {
        regionInfo = weatherDataParser.parseRegionInfo();
        countryInfo = weatherDataParser.parseCountryInfo();
        currentWeather = weatherDataParser.parseCurrentWeatherData();
        perHourWeather = weatherDataParser.parsePerHourWeather();
        weatherForThreeDays = weatherDataParser.parseWeatherForThreeDays();
    }

    /**
     * getter to get the value of a private variable.
     */
    public String getRegionInfo() {
        return regionInfo;
    }

    /**
     * getter to get the value of a private variable.
     */
    public String getCountryInfo() {
        return countryInfo;
    }

    /**
     * getter to get the value of a private variable.
     */
    public String getCurrentWeather() {
        return currentWeather;
    }

    /**
     * getter to get the value of a private variable.
     */
    public Map<String, String> getPerHourWeather() {
        return perHourWeather;
    }

    /**
     * getter to get the value of a private variable.
     */
    public Map<String, Map<String, String>> getWeatherForThreeDays() {
        return weatherForThreeDays;
    }
}
