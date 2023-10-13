package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CurrentWeather {
    private String region;
    private String temp;
}
