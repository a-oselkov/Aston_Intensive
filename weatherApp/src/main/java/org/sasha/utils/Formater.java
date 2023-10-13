package org.sasha.utils;

import lombok.AllArgsConstructor;
import org.sasha.Model.CurrentWeather;
import org.sasha.Model.DayWeather;
import org.sasha.Model.HourWeather;

import static org.sasha.utils.Parser.getHourWeather;
import static org.sasha.utils.Parser.getWeatherForDay;


/**
 * Contains methods for generating the output of the received weather information.
 */
@AllArgsConstructor
public final class Formater {

    private CurrentWeather currentWeather;
    private String dataFromApi;
    private int dayCount = 3 ;
    private int hourCount = 24;

    public String formatCurrentWeatherOutput(CurrentWeather weather) {
        return "Region: " + weather.getRegion()
                + "\nCurrent weather: " + weather.getTemp();
    }

    public String formatPerHourWeatherOutput(String dataFromApi, int hourCount) {
        StringBuilder out = new StringBuilder();
        for (int hour = 0; hour < hourCount; hour++) {
            HourWeather weather = getHourWeather(dataFromApi, hour);
            out.append(getTime(weather.getTime()))
                    .append(" ").append(weather.getTemp()).append("\n");
        }
        return out.toString();
    }

    private String getTime(String time) {
        int index = time.indexOf(" ") + 1;
        return time.substring(index);
    }

    public String formatForThreeDayWeatherOutput(String dataFromApi, int dayCount) {
        StringBuilder out = new StringBuilder();
        for (int day = 0; day < dayCount; day++) {
            DayWeather weather = getWeatherForDay(dataFromApi, day);
            out.append(weather.getDate()).append("\n")
                    .append("min ").append(weather.getTempMin()).append("\n")
                    .append("max ").append(weather.getTempMax()).append("\n")
                    .append("avg ").append(weather.getTempAvg()).append("\n")
                    .append("\n");
        }
        return out.toString();
    }

    public String formatAllOutput() {
        return formatCurrentWeatherOutput(currentWeather) + "\n\nHourly forecast for today:\n"
        + formatPerHourWeatherOutput(dataFromApi, hourCount) + "\nForecast for 3 days:\n"
        + formatForThreeDayWeatherOutput(dataFromApi, dayCount);
    }
}
