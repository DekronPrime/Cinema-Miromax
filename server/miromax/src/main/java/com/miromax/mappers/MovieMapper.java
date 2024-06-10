package com.miromax.mappers;

import com.miromax.dtos.*;
import com.miromax.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper MAPPER = Mappers.getMapper(MovieMapper.class);

    Movie toMovieEntity(MoviePostDto moviePostDto);

    MovieDto toMovieDto(Movie movie);

    MovieComponentDto toMovieComponentDto(Movie movie);

    MovieUpcomingComponentDto toMovieUpcomingComponentDto(Movie movie);

    MovieShortDto toMovieShortDto(Movie movie);
    MovieInfoDto toMovieInfoDto(Movie movie);

    default Page<MovieDto> toMovieDtoPage(Page<Movie> moviePage) {
        return moviePage.map(this::toMovieDto);
    }

    default List<MovieDto> toMovieDtoList(List<Movie> movieList) {
        return movieList.stream()
                .map(this::toMovieDto)
                .collect(Collectors.toList());
    }

    default List<MovieComponentDto> toMovieComponentDtoList(List<Movie> movieList) {
        return movieList.stream()
                .map(this::toMovieComponentDto)
                .collect(Collectors.toList());
    }

    default List<MovieUpcomingComponentDto> toMovieUpcomingComponentDtoList(List<Movie> movieList) {
        return movieList.stream()
                .map(this::toMovieUpcomingComponentDto)
                .collect(Collectors.toList());
    }
}
