package com.miromax.mappers;

import com.miromax.dtos.LocationShortDto;
import com.miromax.models.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface LocationMapper {
    LocationMapper MAPPER = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    LocationShortDto toLocationShortDto(Location location);

    default List<LocationShortDto> toLocationShortDtoList(List<Location> locationList) {
        return locationList.stream()
                .map(this::toLocationShortDto)
                .collect(Collectors.toList());
    }
}
