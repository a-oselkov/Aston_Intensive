package org.sasha.utils;

import lombok.AllArgsConstructor;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.WeatherService;
import org.sasha.service.impl.WeatherServiceImpl;

/**
 * Contains methods for generating the output of the received weather information.
 */

@AllArgsConstructor
public final class Formater {

    public static final int DAY_COUNT = 2;
    public static final int HOUR_COUNT = 23;

    private final WeatherDto weatherData;

    public String formatCurrentWeatherOutput() {
        return "Region: " + weatherData.getLocation().getRegion() +
                "\nToday is: " + weatherData.getLocation().getLocaltime() +
                "\n\nCurrent weather: " + weatherData.getCurrent().getTemp_c();
    }

    public String formatPerHourWeatherOutput() {
        StringBuilder out = new StringBuilder();
        for (int hour = 0; hour < HOUR_COUNT; hour++) {
            out.append(getTime(weatherData.getForecast().getForecastDay().get(0)
                            .getHours().get(hour).getTime()))
                    .append(" ").append(weatherData.getForecast().getForecastDay().get(0)
                            .getHours().get(hour).getTemp_c()).append("\n");
        }
        return out.toString();
    }

    private String getTime(String time) {
        int index = time.indexOf(" ") + 1;
        return time.substring(index);
    }

    public String formatForThreeDayWeatherOutput() {
        StringBuilder out = new StringBuilder();
        for (int day = 0; day <= DAY_COUNT; day++) {
            out.append(weatherData.getForecast().getForecastDay().get(day).getDate()).append("\n")
                    .append("min ").append(weatherData.getForecast().getForecastDay()
                            .get(day).getDay().getMintemp_c()).append("\n")
                    .append("max ").append(weatherData.getForecast().getForecastDay()
                            .get(day).getDay().getMaxtemp_c()).append("\n")
                    .append("avg ").append(weatherData.getForecast().getForecastDay()
                            .get(day).getDay().getAvgtemp_c()).append("\n")
                    .append("\n");
        }
        return out.toString();
    }

    public String formatAllOutput() {
        return formatCurrentWeatherOutput() + "\n\nHourly forecast for today:\n"
        + formatPerHourWeatherOutput() + "\nForecast for 3 days:\n"
        + formatForThreeDayWeatherOutput();
    }
}
