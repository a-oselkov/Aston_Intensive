import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeatherData {
    String regionInfo;
    String countryInfo;
    String currentWeather;
    Map<String, String> perHourWeather = new LinkedHashMap<>();
    Map<String, Map<String, String>> weatherForThreeDays = new LinkedHashMap<>();
}
