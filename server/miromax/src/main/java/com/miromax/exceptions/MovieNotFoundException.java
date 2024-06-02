package com.miromax.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String message) {
        super(message);
    }
}