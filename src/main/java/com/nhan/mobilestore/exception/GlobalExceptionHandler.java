package com.nhan.mobilestore.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object>
        handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                     HttpHeaders httpHeaders,
                                     HttpStatusCode httpStatus,
                                     WebRequest webRequest) {
        Map<String, Object> objectBody = new LinkedHashMap<>();
        objectBody.put("Current Timestamp", new Date());
        objectBody.put("Status", httpStatus.value());

        // Get all errors
        List<String> exceptionalErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        objectBody.put("Errors", exceptionalErrors);
        return new ResponseEntity<>(objectBody, httpStatus);
    }

    /*
    * Handling not found exception
    * @param ex NotFoundException
    * @param request WebRequest
    * @return ResponseEntity
    */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException (
            NotFoundException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("message", "Not found exception");
        body.put("details", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /*
    * To handle the specific exception related to a foreign key constraint conflict
    * @param ex DataIntegrityViolationException
    * @param request WebRequest
    * @return ResponseEntity
    */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleForeignKeyConstraintViolation (
            DataIntegrityViolationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("message", "Data integrity violation");

        // Extract the most relevant message from the DataIntegrityViolationException
        String rootCauseMessage =
                StringUtils.hasText(Objects.requireNonNull(ex.getRootCause()).getMessage())
                        ? Objects.requireNonNull(ex.getRootCause()).getMessage() : ex.getMessage();
        body.put("details", rootCauseMessage);

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /*
    * Triggers when a duplicate resource is being created.
    * @param ex AlreadyExistsException
    * @param request the current web request
    * @return a ResponseEntity with the error details and HttpStatus.CONFLICT
    */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException (
            AlreadyExistsException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /* Triggers when invalid credentials are provided during login.
    * @param ex InvalidCredentialsException
    * @param request the current web request
    * @return a ResponseEntity with the error details and HttpStatus.UNAUTHORIZED
    */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException (
            InvalidCredentialsException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("message", "Invalid credentials");
        body.put("details", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
