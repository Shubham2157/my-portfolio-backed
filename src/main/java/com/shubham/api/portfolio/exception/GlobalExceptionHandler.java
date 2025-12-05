package com.shubham.api.portfolio.exception;

import com.shubham.api.portfolio.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                        HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Not Found");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex,
                                                                HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Internal Server Error");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
