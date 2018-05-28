package com.siq.iqrestmenu.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleExceptions(Exception e) {
        RestErrorResponse errorResponse = new RestErrorResponse();

        errorResponse.setBody(e.getCause() == null? "No cause specified": e.getCause().toString());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<RestErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
