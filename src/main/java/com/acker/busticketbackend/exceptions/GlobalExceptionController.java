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
    ProblemDetail userNotFoundException(UserAlreadyExistException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        problemDetail.setTitle("User Already Exist");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(PasswordMismatchException.class)
    ProblemDetail passwordMismatchException(PasswordMismatchException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail((HttpStatus.FORBIDDEN), e.getMessage());
        problemDetail.setTitle("Password Mismatch");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail userNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("User Email Does not exist");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(InsufficientSeatsException.class)
    ProblemDetail insufficientSeatsException(InsufficientSeatsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, e.getMessage());
        problemDetail.setTitle("Insufficient Seats for give bus");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;

    }

}
