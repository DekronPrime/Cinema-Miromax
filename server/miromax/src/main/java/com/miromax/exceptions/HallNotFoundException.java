package com.miromax.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HallNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public HallNotFoundException(String message) {
        super(message);
    }
}
