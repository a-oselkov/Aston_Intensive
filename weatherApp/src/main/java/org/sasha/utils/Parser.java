package org.sasha.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.sasha.Model.CurrentWeather;
import org.sasha.Model.DayWeather;
import org.sasha.Model.HourWeather;

import java.util.List;
import java.util.Map;

/**
 * Contains methods for processing weather information received from a third-party service.
 */
public final class Parser {

    private static Map<String, Object> parseDataFromApi(String dataFromApi) {
        ObjectMapper mapper = new JsonMapper();
        try {
            return mapper.readValue(dataFromApi, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("data processing error");
        }
    }


    @SuppressWarnings("unchecked")
    public static String getRegionInfo(String dataFromApi) {
        Map<String, String> locationData = ((Map<String, String>) parseDataFromApi(dataFromApi).get("location"));
        return locationData.get("region");
    }

    @SuppressWarnings("unchecked")
    public static CurrentWeather getCurrentWeather(String dataFromApi) {
        Map<String, Double> currentWeatherData =
                ((Map<String, Double>) parseDataFromApi(dataFromApi).get("current"));
        String temp = String.valueOf(currentWeatherData.get("temp_c"));
        String region = getRegionInfo(dataFromApi);
        return new CurrentWeather(region, temp);
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> getForecastData(String dataFromApi) {
        Map<String, List<Map<String, Object>>> forecastData =
                ((Map<String, List<Map<String, Object>>>) parseDataFromApi(dataFromApi).get("forecast"));
        return forecastData.get("forecastday");
    }

    @SuppressWarnings("unchecked")
    public static DayWeather getWeatherForDay(String dataFromApi, int day) {
        String region = getRegionInfo(dataFromApi);
        Map<String, String> dayData = ((Map<String, String>) getForecastData(dataFromApi).get(day).get("day"));
        String minTemp = String.valueOf(dayData.get("mintemp_c"));
        String maxTemp = String.valueOf(dayData.get("maxtemp_c"));
        String avgTemp = String.valueOf(dayData.get("avgtemp_c"));
        String date = String.valueOf(getForecastData(dataFromApi).get(day).get("date"));

        return new DayWeather(region, date, minTemp, maxTemp, avgTemp);
    }

    @SuppressWarnings("unchecked")
    public static HourWeather getHourWeather(String dataFromApi, int hour) {
        String region = getRegionInfo(dataFromApi);
        List<Map<String, Object>> dayData =
                ((List<Map<String, Object>>) getForecastData(dataFromApi).get(0).get("hour"));
        String time = String.valueOf(dayData.get(hour).get("time"));
        String temp = String.valueOf(dayData.get(hour).get("temp_c"));
        dayData.clear();

        return new HourWeather(region, time, temp);
    }
}
