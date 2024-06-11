package com.acker.busticketbackend.exceptions;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {
   @ExceptionHandler(UserAlreadyExistException.class)
   ProblemDetail userNotFoundException(UserAlreadyExistException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("User Already Exist");
        // problemDetail.setType(URI.create("https://example.com/something-not-found"));
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
   }
}
