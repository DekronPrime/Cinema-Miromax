package com.miromax.controllers;

import com.miromax.dtos.MovieComponentDto;
import com.miromax.dtos.MovieDto;
import com.miromax.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/components")
    @CrossOrigin
    public List<MovieComponentDto> getActiveMoviesWithSessionsForToday() {
        return movieService.getActiveMoviesWithSessionsByDate(LocalDate.now());
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }
}

