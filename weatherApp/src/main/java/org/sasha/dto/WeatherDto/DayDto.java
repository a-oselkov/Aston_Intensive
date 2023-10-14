package org.sasha.dto.WeatherDto;

import lombok.Data;

/**
 * Отображение прогноза о конкретном дне
 */
@Data
public class DayDto {
    private String maxtemp_c;
    private String mintemp_c;
    private String avgtemp_c;
}
