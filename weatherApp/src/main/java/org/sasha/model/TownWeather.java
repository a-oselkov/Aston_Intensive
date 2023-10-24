package org.sasha.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TownWeather implements Comparable<TownWeather> {
    Long id;
    private String name;
    private String temp;
    private String feelsLikeTemp;
    private String cloud;

    @Override
    public int compareTo(TownWeather o) {
        return this.temp.compareTo(o.getTemp());
    }
}
