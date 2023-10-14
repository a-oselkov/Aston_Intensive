package org.sasha.dto.WeatherDto;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
public class ForecastDto {
    @JsonSetter("forecastday")
    private List<ForecastDayDto> forecastDay;
}
