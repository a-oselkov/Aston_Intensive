package org.sasha.service;

import org.sasha.Model.CurrentWeather;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;

import java.util.List;
import java.util.Optional;

public interface CurrentWeatherService {
    void save(CurrentDto dto, LocationDto location);
    List<CurrentWeather> findAll();
    Optional<CurrentWeather> findByRegion(String regionName);
}
