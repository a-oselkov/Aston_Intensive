package org.sasha.service;

import jakarta.transaction.Transactional;
import org.sasha.dto.CurrentWeatherCheckDto;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.model.CurrentWeatherCheck;
import org.sasha.model.User;

import java.util.List;
import java.util.Optional;
@Transactional
public interface CurrentWeatherCheckService {
    void save(CurrentWeatherCheckDto dto);
    //public Optional<CurrentWeatherCheck> findLastCheckByUser(User user);
    //List<CurrentWeather> findAll();
    //Optional<CurrentWeather> findByRegion(String regionName);
}
