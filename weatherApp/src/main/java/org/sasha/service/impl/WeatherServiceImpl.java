package org.sasha.service.impl;

import org.sasha.dao.WeatherDao;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.WeatherService;

import static org.sasha.utils.Parser.parseDataFromApi;

public class WeatherServiceImpl implements WeatherService {
    private final WeatherDao weather = new WeatherDao();
    @Override
    public WeatherDto getWetherData(String weatherApiUrl) {
        return weather.getWeatherData(weatherApiUrl);
    }
}
