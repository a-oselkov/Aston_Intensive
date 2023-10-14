package org.sasha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TownWeatherDto {
    private String name;
    private String temp;
    private String feelsLikeTemp;
    private String cloud;
}
