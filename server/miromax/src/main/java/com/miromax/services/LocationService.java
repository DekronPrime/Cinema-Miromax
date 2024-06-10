package com.miromax.services;

import com.miromax.dtos.LocationShortDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<LocationShortDto> getAllLocations();
}
