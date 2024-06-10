package com.miromax.dtos;

import lombok.Data;

@Data
public class LocationShortDto {
    private Long id;
    private Long cityId;
    private String cityName;
    private String shoppingMall;
}
