package org.sasha.service;

import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;

public interface CurrentWeatherService {
    void save(CurrentDto dto, LocationDto location);
//    CurrentDto getCurrentWeatherData(String weatherApiUrl);
}
