package org.sasha.service;

import org.sasha.model.TownWeather;
import org.sasha.dto.TownWeatherDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TownWeatherService {
    void save(TownWeatherDto townWeather);
    Optional<TownWeather> findById(Long id) throws SQLException;
    List<TownWeather> findAll() throws SQLException;
    void deleteById(Long id) throws SQLException;
    void deleteAll();
    TownWeatherDto getTownWeatherData(String weatherApiUrl);

}
