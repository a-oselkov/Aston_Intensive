package org.sasha.utils;

import lombok.AllArgsConstructor;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.WeatherService;

/**
 * Contains methods for generating the output of the received weather information.
 */
@AllArgsConstructor
public final class Formater {

    public static final int DAY_COUNT = 2;
    public static final int HOUR_COUNT = 23;

    private String dataFromApi;

    private WeatherService weatherService;

    public String formatCurrentWeatherOutput() {
        WeatherDto weather = weatherService.getWetherInfo(dataFromApi);
        return "Region: " + weather.getLocation().getRegion() +
                "\nToday is: " + weather.getLocation().getLocaltime() +
                "\n\nCurrent weather: " + weather.getCurrent().getTemp_c();
    }

    public String formatPerHourWeatherOutput(String dataFromApi) {
        StringBuilder out = new StringBuilder();
        for (int hour = 0; hour < HOUR_COUNT; hour++) {
            WeatherDto weather = weatherService.getWetherInfo(dataFromApi);
            out.append(getTime(weather.getForecast().getForecastDay().get(0)
                            .getHours().get(hour).getTime()))
                    .append(" ").append(weather.getForecast().getForecastDay().get(0)
                            .getHours().get(hour).getTemp_c()).append("\n");
        }
        return out.toString();
    }

    private String getTime(String time) {
        int index = time.indexOf(" ") + 1;
        return time.substring(index);
    }

    public String formatForThreeDayWeatherOutput(String dataFromApi) {
        StringBuilder out = new StringBuilder();
        for (int day = 0; day <= DAY_COUNT; day++) {
            WeatherDto weather = weatherService.getWetherInfo(dataFromApi);
            out.append(weather.getForecast().getForecastDay().get(day).getDate()).append("\n")
                    .append("min ").append(weather.getForecast().getForecastDay()
                            .get(day).getDay().getMintemp_c()).append("\n")
                    .append("max ").append(weather.getForecast().getForecastDay()
                            .get(day).getDay().getMaxtemp_c()).append("\n")
                    .append("avg ").append(weather.getForecast().getForecastDay()
                            .get(day).getDay().getAvgtemp_c()).append("\n")
                    .append("\n");
        }
        return out.toString();
    }

    public String formatAllOutput() {
        return formatCurrentWeatherOutput() + "\n\nHourly forecast for today:\n"
        + formatPerHourWeatherOutput(dataFromApi) + "\nForecast for 3 days:\n"
        + formatForThreeDayWeatherOutput(dataFromApi);
    }
}
