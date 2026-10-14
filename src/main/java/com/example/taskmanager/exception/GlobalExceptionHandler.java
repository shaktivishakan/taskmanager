package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Catches exceptions thrown anywhere in the controllers
// and turns them into clean JSON error responses.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Runs when the service throws TaskNotFoundException (e.g. GET /api/tasks/999).
    // Returns 404 with a short message, instead of Spring's default 500.
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),   // 404 as a number, for the JSON body
                ex.getMessage(),                // "Task not found with id 999"
                LocalDateTime.now()
        );
        // .status() sets the real HTTP status code; .body() is the JSON content
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Runs when @Valid fails on a request body (e.g. empty title).
    // Collects every failed field so the client can fix all of them at once.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        // field name -> error message, e.g. "title" -> "Title is required"
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Validation failed",            // short summary; details are in 'errors'
                errors,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}