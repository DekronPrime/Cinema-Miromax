package com.miromax.services.impl;

import com.miromax.dtos.MovieComponentDto;
import com.miromax.dtos.MovieDto;
import com.miromax.dtos.MoviePostDto;
import com.miromax.exceptions.MovieNotFoundException;
import com.miromax.mappers.MovieMapper;
import com.miromax.models.Movie;
import com.miromax.models.enums.Status;
import com.miromax.repositories.*;
import com.miromax.services.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final CountryRepository countryRepository;

    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository, DirectorRepository directorRepository, CountryRepository countryRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<MovieComponentDto> getActiveMoviesWithSessionsByDate(LocalDate date) {
        List<Movie> movies = movieRepository.findActiveMoviesWithSessions(Status.ACTIVE, Status.UPCOMING, date);
        return movies.stream()
                .map(MovieMapper.MAPPER::toMovieComponentDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto findMovieById(Long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        Movie movie = optionalMovie.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
        return MovieMapper.MAPPER.toMovieDto(movie);
    }

    @Override
    public MovieDto createMovie(MoviePostDto moviePostDto) {
        Movie movie = MovieMapper.MAPPER.toMovieEntity(moviePostDto);
        movie.addGenres(genreRepository.findAllById(moviePostDto.getGenreIds()));
//        movie.addCountries(countryRepository.findAllById(moviePostDto.getCountryIds()));
        movie.setCountry(countryRepository.findById(moviePostDto.getCountryId()).orElseThrow());
        movie.addDirectors(directorRepository.findAllById(moviePostDto.getDirectorIds()));
        movie.addActors(actorRepository.findAllById(moviePostDto.getActorIds()));
        movie.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        LocalDate today = LocalDate.now();
        if (moviePostDto.getStartRentalDate().isAfter(today)) {
            movie.setStatus(Status.UPCOMING);
        } else if (moviePostDto.getFinalRentalDate().isBefore(today)) {
            movie.setStatus(Status.EXPIRED);
        } else {
            movie.setStatus(Status.ACTIVE);
        }
        Movie savedMovie = movieRepository.save(movie);
        return MovieMapper.MAPPER.toMovieDto(savedMovie);
    }
}
