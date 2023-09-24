package com.education.projects.places.manager.controller;

import com.education.projects.places.manager.dto.response.ErrorResponse;
import com.education.projects.places.manager.dto.response.ValidationErrorResponse;
import com.education.projects.places.manager.dto.response.Violation;
import com.education.projects.places.manager.exception.CountryNotFoundException;
import com.education.projects.places.manager.exception.PlaceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ValidationErrorResponse> onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    ResponseEntity<ErrorResponse> onPlaceNotFoundException(Exception e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    ResponseEntity<ErrorResponse> onCountryNotFoundException(Exception e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> onErrorResponse(Exception e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    ResponseEntity<ErrorResponse> onServerErrorResponse(HttpServerErrorException.InternalServerError e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        log.error("Error: {}", error);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
