package com.miromax.dtos;

import com.miromax.models.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionInfoDto {
    private Long id;
    private LocalDateTime startTime;
    private int[] prices;
    private Status status;
}
