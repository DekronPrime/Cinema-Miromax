package com.miromax.dtos;

import com.miromax.models.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieShortDto {
    private Long id;
    private String posterUrl;
    private String trailerUrl;
    private Integer likes;
    private Integer dislikes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
