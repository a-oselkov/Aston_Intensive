import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherData {
    private String regionInfo;
    private String countryInfo;
    private String currentWeather;
    private Map<String, String> perHourWeather = new LinkedHashMap<>();
    private Map<String, Map<String, String>> weatherForThreeDays = new LinkedHashMap<>();

    public void fillWeatherData(WeatherDataParser weatherDataParser) {
        regionInfo = weatherDataParser.parseRegionInfo();
        countryInfo = weatherDataParser.parseCountryInfo();
        currentWeather = weatherDataParser.parseCurrentWeatherData();
        perHourWeather = weatherDataParser.parsePerHourWeather();
        weatherForThreeDays = weatherDataParser.parseWeatherForThreeDays();
    }

    public String getRegionInfo() {
        return regionInfo;
    }

    public String getCountryInfo() {
        return countryInfo;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public Map<String, String> getPerHourWeather() {
        return perHourWeather;
    }

    public Map<String, Map<String, String>> getWeatherForThreeDays() {
        return weatherForThreeDays;
    }
}
