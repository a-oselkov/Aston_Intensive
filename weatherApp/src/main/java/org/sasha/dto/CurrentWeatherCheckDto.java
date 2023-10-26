package org.sasha.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sasha.model.Location;
import org.sasha.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeatherCheckDto {
    private String temp_c;
    private String feelsLike_c;
    private String cloud;
    private Long user_id;
    private Long location_id;
}
