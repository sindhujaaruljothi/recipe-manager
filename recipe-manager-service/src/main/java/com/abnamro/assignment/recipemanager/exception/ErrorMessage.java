package com.abnamro.assignment.recipemanager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ErrorMessage {
    private final HttpStatus httpStatus;
    private final String message;
}
