package org.sasha.service.impl;

import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.service.WeatherService;

import static org.sasha.utils.Parser.parseDataFromApi;

public class WeatherServiceImpl implements WeatherService {
    @Override
    public WeatherDto getWetherInfo(String dataFromApi) {
        return parseDataFromApi(dataFromApi);
    }
}
