package org.sasha.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.sasha.dto.TownWeatherDto;
import org.sasha.dto.WeatherDto.WeatherDto;

/**
 * Contains methods for processing weather information received from a third-party service.
 */

public final class Parser {

    public static WeatherDto parseDataFromApi(String dataFromApi) {
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
