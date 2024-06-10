package com.miromax.services.impl;

import com.miromax.dtos.LocationShortDto;
import com.miromax.mappers.LocationMapper;
import com.miromax.repositories.LocationRepository;
import com.miromax.services.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<LocationShortDto> getAllLocations() {
        return LocationMapper.MAPPER.toLocationShortDtoList(locationRepository.findAll());
    }
}
