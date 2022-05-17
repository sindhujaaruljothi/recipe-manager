package com.abnamro.assignment.recipemanager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnAuthorizedException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public UnAuthorizedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
        this.message = message;
    }
}
