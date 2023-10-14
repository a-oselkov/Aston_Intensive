package org.sasha.dto.WeatherDto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;


/**
 * Отображение прогноза погоды на конкретный день.
 */
@Data
public class ForecastDayDto {

    private String date;
    private DayDto day;

    @JsonSetter("hour")
    private List<HourDto> hours;

}
