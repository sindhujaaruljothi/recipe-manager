package com.abnamro.assignment.recipemanager.exception;

import com.abnamro.assignment.recipemanager.controller.IngredientController;
import com.abnamro.assignment.recipemanager.controller.RecipeController;
import com.abnamro.assignment.recipemanager.controller.UnitController;
import com.abnamro.assignment.recipemanager.controller.UserManagementController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {
        UserManagementController.class,
        RecipeController.class,
        IngredientController.class,
        UnitController.class
})
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {UnAuthorizedException.class})
    protected ResponseEntity<ErrorMessage> UnAuthorizedException(UnAuthorizedException exception) {
            return new ResponseEntity<>(
                    new ErrorMessage(exception.getHttpStatus(),
                   exception.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ErrorMessage> BadRequestException(BadRequestException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getHttpStatus(),
                        exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorMessage> NotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getHttpStatus(),
                        exception.getMessage()),HttpStatus.NOT_FOUND);
    }
}
