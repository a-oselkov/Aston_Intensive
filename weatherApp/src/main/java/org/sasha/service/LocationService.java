package org.sasha.service;

import org.sasha.model.Location;
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
