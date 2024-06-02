package com.miromax.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miromax.models.enums.ActorPopularity;
import lombok.Data;

@Data
public class ActorDto {
    private Long id;
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String middleName;
    private String lastName;
    private ActorPopularity popularity;
    private CountryShortDto country;
}