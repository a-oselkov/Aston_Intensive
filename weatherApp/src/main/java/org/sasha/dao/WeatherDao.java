package org.sasha.dao;

import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.utils.Request;

import static org.sasha.utils.Parser.parseDataFromApi;

public class WeatherDao {
    public WeatherDto getWeatherData (String weaterApiUrl) {
        String dataFromApi = Request.get(weaterApiUrl).getBody();
        return parseDataFromApi(dataFromApi);
    }
}