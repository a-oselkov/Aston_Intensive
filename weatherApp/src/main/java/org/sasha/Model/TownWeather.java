package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TownWeather {
    Long id;
    private String name;
    private String temp;
    private String feelsLikeTemp;
    private String cloud;
}
