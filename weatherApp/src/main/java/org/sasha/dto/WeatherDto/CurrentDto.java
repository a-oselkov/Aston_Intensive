package org.sasha.dto.WeatherDto;

import lombok.Data;

@Data
public class CurrentDto {
    private String temp_c;
    private String feelslike_c;
    private String cloud;
}
