package org.sasha.dto.WeatherDto;

import lombok.Data;

/**
 * Отображение погоды.
 */
@Data
public class WeatherDto {
    private LocationDto location;
    private CurrentDto current;
    private ForecastDto forecast;

}
