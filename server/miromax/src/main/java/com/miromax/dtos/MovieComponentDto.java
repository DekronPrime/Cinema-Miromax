package com.miromax.dtos;

import com.miromax.models.enums.AgeLimit;
import com.miromax.models.enums.Format;
import com.miromax.models.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieComponentDto {
    private Long id;
    private String titleUkr;
    private String titleOriginal;
    private String posterUrl;
    private Integer year;
    private List<GenreDto> genres;
    private Double imdbRating;
    private Integer duration;
    private List<DirectorDto> directors;
    private AgeLimit ageLimit;
    private Format format;
    private String trailerUrl;
    private LocalDate startRentalDate;
    private LocalDate finalRentalDate;
    private List<SessionInfoDto> sessions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}