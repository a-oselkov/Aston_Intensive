package org.sasha.service;

import jakarta.transaction.Transactional;
import org.sasha.model.Location;
import org.sasha.dto.WeatherDto.LocationDto;

import java.util.List;
import java.util.Optional;

@Transactional
public interface LocationService {
    void save(LocationDto dto);
    Optional<Location> findById(Long id);
    List<Location> findAll();
    void deleteById(Long id);
    void deleteAll();
    Optional<Location> findByRegion(String region);
    LocationDto getLocationData(String weatherApiUrl);
}
