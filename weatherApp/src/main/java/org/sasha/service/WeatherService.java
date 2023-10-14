package org.sasha.service;

import org.sasha.dto.WeatherDto.WeatherDto;

public interface WeatherService {
    WeatherDto getWetherInfo(String dataFromApi);
}
