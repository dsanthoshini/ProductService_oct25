package com.example.productservice_oct25.ControllerAdvicer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
//if runtime exception occurs this method will be called
    public ResponseEntity<Void> handleruntimeexception(RuntimeException re) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        //void means body is null == no body
    }
//null pointer exception will come when product id is not found
//so we are passing meaningfull msg to client to try again with valid product id
@ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException npe) {
        return new ResponseEntity<>("please try again with valid product id.",
                HttpStatus.NOT_FOUND);
    }









}
