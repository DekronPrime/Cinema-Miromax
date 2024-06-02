package com.miromax.services;


import com.miromax.dtos.MovieComponentDto;
import com.miromax.dtos.MovieDto;
import com.miromax.dtos.MoviePostDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MovieService {
    List<MovieComponentDto> getActiveMoviesWithSessionsByDate(LocalDate date);

    MovieDto findMovieById(Long id);

    MovieDto createMovie(MoviePostDto moviePostDto);
}
