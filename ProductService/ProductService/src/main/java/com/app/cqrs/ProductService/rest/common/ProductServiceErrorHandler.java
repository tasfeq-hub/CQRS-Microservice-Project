package com.app.cqrs.ProductService.rest.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ProductServiceErrorHandler {

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());

        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    };

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());

        return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    };
}
