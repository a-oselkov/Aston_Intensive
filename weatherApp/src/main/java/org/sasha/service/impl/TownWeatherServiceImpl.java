package org.sasha.service.impl;

import org.sasha.Model.TownWeather;
import org.sasha.dao.TownWeatherDao;
import org.sasha.dto.TownWeatherDto;
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
}
