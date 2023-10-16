package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

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
