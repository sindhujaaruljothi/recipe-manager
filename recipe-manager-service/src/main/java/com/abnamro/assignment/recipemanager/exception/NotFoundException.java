package com.abnamro.assignment.recipemanager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;

    public NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.message = message;
    }
}
