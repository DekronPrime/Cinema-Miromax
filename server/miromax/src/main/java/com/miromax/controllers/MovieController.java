package com.miromax.controllers;

import com.miromax.dtos.*;
import com.miromax.services.LocationService;
import com.miromax.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MovieController {
    private final MovieService movieService;
    private final LocationService locationService;

    public MovieController(MovieService movieService, LocationService locationService) {
        this.movieService = movieService;
        this.locationService = locationService;
    }

    @GetMapping("/cinema-locations")
    public ResponseEntity<List<LocationShortDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/components")
    public ResponseEntity<List<MovieComponentDto>> getActiveMoviesWithSessionsForToday(@RequestParam Long locationId) {
        return ResponseEntity.ok(movieService.getActiveMoviesWithSessionsByDate(LocalDate.now(), locationId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieUpcomingComponentDto>> getUpcomingMovies() {
        return ResponseEntity.ok(movieService.getUpcomingMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieShortDto> getMovieShortInfoById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<MovieInfoDto> getMovieInfoById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieInfoById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieComponentDto>> searchInMovies(@RequestParam String value) {
        return ResponseEntity.ok(movieService.searchInMovies(value));
    }
}

