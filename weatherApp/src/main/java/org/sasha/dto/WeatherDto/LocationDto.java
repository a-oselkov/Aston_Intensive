package org.sasha.dto.WeatherDto;

import lombok.Data;

@Data
public class LocationDto {
    private String region;
    private String country;
    private String localtime;
}
