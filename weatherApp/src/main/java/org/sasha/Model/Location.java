package org.sasha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private Long id;
    private String region;
    private String country;
    private String localtime;
}
