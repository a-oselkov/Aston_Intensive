package org.sasha.service;

import org.sasha.Model.Location;
import org.sasha.Model.TownWeather;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.LocationDto;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    void save(LocationDto dto);
    Optional<Location> findById(Long id);
    List<Location> findAll();
    void deleteById(Long id);
    void deleteAll();
    LocationDto getLocationData(String weatherApiUrl);
}
