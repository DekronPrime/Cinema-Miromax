package com.miromax.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class DirectorDto {
    private Long id;
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String middleName;
    private String lastName;
    private CountryShortDto country;
}
