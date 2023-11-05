package org.sasha.service.impl;

import org.sasha.dao.CurrentWeatherCheckDao;
import org.sasha.dto.CurrentWeatherCheckDto;
import org.sasha.model.CurrentWeatherCheck;
import org.sasha.model.User;
import org.sasha.service.CurrentWeatherCheckService;

import java.util.Optional;

public class CurrentWeatherCheckServiceImpl implements CurrentWeatherCheckService {
    private final CurrentWeatherCheckDao currentWeatherCheckDao = new CurrentWeatherCheckDao();

    @Override
    public void save(CurrentWeatherCheckDto dto) {
        currentWeatherCheckDao.save(dto);
    }

//    @Override
//    public Optional<CurrentWeatherCheck> findLastCheckByUser(User user) {
//        return currentWeatherCheckDao.findLastCheckByUser(user);
//    }

//    @Override
//    public List<CurrentWeather> findAll() {
//        return currentWeatherDao.findAll();
//    }
//
//    @Override
//    public Optional<CurrentWeather> findByRegion(String regionName) {
//        return currentWeatherDao.findByRegion(regionName);
//    }
//
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
