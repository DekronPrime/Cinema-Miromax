package com.miromax.dtos;


import com.miromax.models.enums.AgeLimit;
import com.miromax.models.enums.DubLanguage;
import com.miromax.models.enums.Format;
import com.miromax.models.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private String titleUkr;
    private String titleOriginal;
    private String posterUrl;
    private Integer year;
    private List<GenreDto> genres;
    private DubLanguage dubLanguage;
//    private List<CountryDto> countries;
    private CountryDto country;
    private List<DirectorDto> directors;
    private AgeLimit ageLimit;
    private Integer duration;
    private Double imdbRating;
    private String description;
    private String trailerUrl;
    private Format format;
    private Integer likes;
    private Integer dislikes;
    private List<ActorDto> actors;
    private LocalDate startRentalDate;
    private LocalDate finalRentalDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}