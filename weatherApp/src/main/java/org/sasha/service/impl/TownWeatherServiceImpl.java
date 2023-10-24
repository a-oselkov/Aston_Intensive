package org.sasha.service.impl;

import org.sasha.model.TownWeather;
import org.sasha.dao.TownWeatherDao;
import org.sasha.dao.WeatherDao;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.TownWeatherService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TownWeatherServiceImpl implements TownWeatherService {

    private final TownWeatherDao townWeatherDao = new TownWeatherDao();

    @Override
    public void save(TownWeatherDto townWeather) {
        townWeatherDao.save(townWeather);
    }

    @Override
    public Optional<TownWeather> findById(Long id) throws SQLException {
        return townWeatherDao.findById(id);
    }

    @Override
    public List<TownWeather> findAll() throws SQLException {
        return townWeatherDao.findAll();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        townWeatherDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        townWeatherDao.deleteAll();
    }

    @Override
    public TownWeatherDto getTownWeatherData(String weatherApiUrl) {
        WeatherDao weatherDao = new WeatherDao();
        WeatherDto weather = weatherDao.getWeatherData(weatherApiUrl);

        String name = weather.getLocation().getRegion();
        String temp = weather.getCurrent().getTemp_c();
        String feelsLike = weather.getCurrent().getFeelslike_c();
        String cloud = weather.getCurrent().getCloud();

        return new TownWeatherDto(name, temp, feelsLike, cloud);

    }
}
