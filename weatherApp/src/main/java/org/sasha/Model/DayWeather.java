package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayWeather {
    private String region;
    private String date;
    private String tempMin;
    private String tempMax;
    private String tempAvg;
}
