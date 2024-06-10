package com.miromax.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationDto {
    private Long id;
    private Long cityId;
    private String cityName;
    private String shoppingMall;
    private String address;
    private String details;
    private String mapsUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
