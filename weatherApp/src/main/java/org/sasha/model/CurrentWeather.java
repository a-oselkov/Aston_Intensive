package org.sasha.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentWeather {
    private Long id;
    private String temp_c;
    private String feelsLike_c;
    private String cloud;

    private Long locationId;
}
