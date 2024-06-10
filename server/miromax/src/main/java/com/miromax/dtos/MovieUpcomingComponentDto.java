package com.miromax.dtos;

import com.miromax.models.enums.AgeLimit;
import com.miromax.models.enums.Format;
import com.miromax.models.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieUpcomingComponentDto {
    private Long id;
    private String titleUkr;
    private String titleOriginal;
    private String posterUrl;
    private Integer year;
    private List<GenreDto> genres;
    private Double imdbRating;
    private Integer duration;
    private List<DirectorDto> directors;
    private List<ActorDto> actors;
    private String trailerUrl;
    private LocalDate startRentalDate;
    private LocalDate finalRentalDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
