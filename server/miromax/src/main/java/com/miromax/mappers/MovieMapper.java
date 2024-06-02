package com.miromax.mappers;

import com.miromax.dtos.*;
import com.miromax.models.Movie;
import com.miromax.models.Session;
import com.miromax.models.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper MAPPER = Mappers.getMapper(MovieMapper.class);

    Movie toMovieEntity(MoviePostDto moviePostDto);

    MovieDto toMovieDto(Movie movie);
//    @AfterMapping
//    default void mapActors(Movie movie, @MappingTarget MovieDto movieDto) {
//        if (movie.getActors() != null) {
//            List<ActorDto> actorDtos = movie.getActors().stream()
//                    .map(actor -> {
//                        ActorDto actorDto = new ActorDto();
//                        actorDto.setId(actor.getId());
//                        actorDto.setFirstName(actor.getFirstName());
//                        actorDto.setMiddleName(actor.getMiddleName());
//                        actorDto.setLastName(actor.getLastName());
//                        actorDto.setPopularity(actor.getPopularity());
//                        actorDto.setCountry(CountryMapper.MAPPER.toCountryShortDto(actor.getCountry())); // Мапуємо країну
//                        return actorDto;
//                    })
//                    .collect(Collectors.toList());
//            movieDto.setActors(actorDtos);
//        }
//    }

    MovieComponentDto toMovieComponentDto(Movie movie);

    default Page<MovieDto> toMovieDtoPage(Page<Movie> moviePage) {
        return moviePage.map(this::toMovieDto);
    }

    default List<MovieDto> toMovieDtoList(List<Movie> movieList) {
        return movieList.stream()
                .map(this::toMovieDto)
                .collect(Collectors.toList());
    }

    default List<SessionInfoDto> mapSessions(List<Session> sessions) {
        return sessions.stream()
                .filter(session -> session.getStatus() == Status.UPCOMING && session.getStartTime().toLocalDate().equals(LocalDate.now()))
                .map(this::toSessionInfoDto)
                .collect(Collectors.toList());
    }

    SessionInfoDto toSessionInfoDto(Session session);
}
