package com.codingtest.api.controller;


import com.codingtest.api.exception.DuplicateResourceException;
import com.codingtest.api.exception.ResourceNotFoundException;
import com.codingtest.api.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public final ResponseEntity<ExceptionResponse> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

    }
}
