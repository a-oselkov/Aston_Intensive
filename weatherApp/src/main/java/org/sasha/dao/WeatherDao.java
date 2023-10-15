package org.sasha.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.sasha.dto.WeatherDto.WeatherDto;
import org.sasha.utils.Request;

public class WeatherDao {
    public WeatherDto getWeatherData(String weatherApiUrl) {
        String dataFromApi = getDataFromExternalApi(weatherApiUrl);
        return parseDataFromApi(dataFromApi);
    }

    private String getDataFromExternalApi(String weatherApiUrl) {
        return Request.get(weatherApiUrl).getBody();
    }

    private WeatherDto parseDataFromApi(String dataFromApi) {
        ObjectMapper mapper = new JsonMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
        try {
            return mapper.readValue(dataFromApi, WeatherDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("data parsing error");
        }
    }
}