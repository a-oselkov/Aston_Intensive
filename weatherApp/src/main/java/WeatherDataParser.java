import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains methods for processing weather information received from a third-party service.
 */
public final class WeatherDataParser {

    /**
     * String, information received from the response to a request to a third-party service.
     */
    private final String inputWeatherData;

    /**
     * Creates an object of the Weather Data Parser type containing information for further processing.
     * @param inputWeatherData information received from the response to a request to a third-party service.
     */
    WeatherDataParser(String inputWeatherData) {
        this.inputWeatherData = inputWeatherData;
    }

    /**
     * Obtaining information for further use and processing.
     * @return an object for further processing and extraction of information
     */
    private Map<String, Object> parseDataFromApi() {
        ObjectMapper mapper = new JsonMapper();
        try {
            return mapper.readValue(inputWeatherData, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("data processing error");
        }
    }

    /**
     * Getting information about the region from the total amount of information.
     * @return a line with information about the region
     */
    @SuppressWarnings("unchecked")
    public String parseRegionInfo() {
        return  ((Map<String, String>) parseDataFromApi().get("location")).get("region");
    }

    /**
     * Getting information about the country from the total amount of information .
     * @return a line with information about the country
     */
    @SuppressWarnings("unchecked")
    public String parseCountryInfo() {
        return ((Map<String, String>) parseDataFromApi().get("location")).get("country");
    }

    /**
     * Getting information about the current weather from the total amount of information.
     * @return a line with information about the current weather
     */
    @SuppressWarnings("unchecked")
    public String parseCurrentWeatherData() {
        return String.valueOf(((Map<String, Double>) parseDataFromApi().get("current")).get("temp_c"));
    }

    /**
     * Getting information about the forecast for three days from the total amount of information.
     * @return Map type object with information about the weather forecast for three days
     */
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> parseWeatherForThreeDays() {
        Map<String, Map<String, String>> weatherForThreeDays = new LinkedHashMap<>();

        String min;
        String max;
        String avg;
        String date;

        Map<String, List<Map<String, Object>>> forecastData =
                ((Map<String, List<Map<String, Object>>>) parseDataFromApi().get("forecast"));
        List<Map<String, Object>> forecastdayData = forecastData.get("forecastday");

        for (int i = 0; i < 3; i++) {
            Map<String, String> weatherForNextDay = new LinkedHashMap<>();
            Map<String, String> dayData = ((Map<String, String>) forecastdayData.get(i).get("day"));
            min = String.valueOf(dayData.get("mintemp_c"));
            max = String.valueOf(dayData.get("maxtemp_c"));
            avg = String.valueOf(dayData.get("avgtemp_c"));
            date = String.valueOf(forecastdayData.get(i).get("date"));

            weatherForNextDay.put("min", min);
            weatherForNextDay.put("max", max);
            weatherForNextDay.put("avg", avg);

            weatherForThreeDays.put(date, weatherForNextDay);
        }
        return weatherForThreeDays;
    }

    /**
     * Getting information about the hourly weather forecast from the total amount of information.
     * @return Map type object with information about the hourly weather forecast
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> parsePerHourWeather() {
        Map<String, String> perHourWeather = new LinkedHashMap<>();

        Map<String, List<Map<String, Object>>> forecastData =
                ((Map<String, List<Map<String, Object>>>) parseDataFromApi().get("forecast"));
        List<Map<String, Object>> forecastdayData = forecastData.get("forecastday");

        List<Map<String, Object>> hourlyWeather = (List<Map<String, Object>>) forecastdayData.get(0).get("hour");

        for (int i = 0; i < 24; i++) {
            String time = i < 10 ? "00-0" + i : "00-" + i;
            Map<String, Object> hourData = hourlyWeather.get(i);
            perHourWeather.put(time, String.valueOf(hourData.get("temp_c")));
        }
        return perHourWeather;
    }
}
