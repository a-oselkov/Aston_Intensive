package org.sasha.service.impl;

import org.sasha.model.CurrentWeather;
import org.sasha.dao.CurrentWeatherDao;
import org.sasha.dto.WeatherDto.CurrentDto;
import org.sasha.dto.WeatherDto.LocationDto;
import org.sasha.service.CurrentWeatherService;

import java.util.List;
import java.util.Optional;

public class CurrentWeatherServiceImpl implements CurrentWeatherService {
    private final CurrentWeatherDao currentWeatherDao = new CurrentWeatherDao();

    @Override
    public void save(CurrentDto dto, LocationDto location) {
        currentWeatherDao.save(dto, location);
    }

    @Override
    public List<CurrentWeather> findAll() {
        return currentWeatherDao.findAll();
    }

    @Override
    public Optional<CurrentWeather> findByRegion(String regionName) {
        return currentWeatherDao.findByRegion(regionName);
    }

//    @Override
//    public CurrentDto getCurrentWeatherData(String weatherApiUrl) {
//        WeatherDao weatherDao = new WeatherDao();
//        WeatherDto weather = weatherDao.getWeatherData(weatherApiUrl);
//
//        String temp = weather.getCurrent().getTemp_c();
//        String feelsLike = weather.getCurrent().getFeelslike_c();
//        String cloud = weather.getCurrent().getCloud();
//
//        return new CurrentDto(temp, feelsLike, cloud);
//    }
}
