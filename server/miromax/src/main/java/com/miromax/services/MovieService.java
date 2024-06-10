package com.miromax.services;


import com.miromax.dtos.*;
import com.miromax.models.enums.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MovieService {
    List<MovieComponentDto> getActiveMoviesWithSessionsByDate(LocalDate date, Long locationId);

    List<MovieUpcomingComponentDto> getUpcomingMovies();

    MovieShortDto findMovieById(Long id);
    MovieInfoDto findMovieInfoById(Long id);

    MovieDto createMovie(MoviePostDto moviePostDto);

    List<MovieComponentDto> searchInMovies(String value);
}
