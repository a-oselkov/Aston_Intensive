package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HourWeather {
    private String region;
    private String time;
    private String temp;
}
