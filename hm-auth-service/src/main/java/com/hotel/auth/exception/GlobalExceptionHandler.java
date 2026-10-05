package com.hotel.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * Handle application Validation exceptions.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        HttpStatus status = ex.getStatus();

        log.warn(
                "Validation exception. errorCode={}, message={}, path={}",
                ex.getStatus().value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return buildResponse(
                status,
                status.name(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }


    /**
     * Handle @Valid request body validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorDetail> errors = new ArrayList<>();

        for (FieldError fieldError :
                ex.getBindingResult().getFieldErrors()) {

            errors.add(
                    new ErrorDetail(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    )
            );
        }

        log.warn(
                "Validation failed. path={}, errors={}",
                request.getRequestURI(),
                errors.size()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                "Validation failed",
                request.getRequestURI(),
                errors
        );
    }


    /**
     * Handle request parameter/path variable validation.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ErrorDetail> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(
                violation -> errors.add(
                        new ErrorDetail(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                "Validation failed",
                request.getRequestURI(),
                errors
        );
    }


    /**
     * Handle invalid JSON request.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn(
                "Invalid request body. path={}",
                request.getRequestURI()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                "Invalid request body",
                request.getRequestURI(),
                null
        );
    }


    /**
     * Handle unsupported HTTP methods.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "METHOD_NOT_ALLOWED",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }


    /**
     * Handle database constraint errors.
     *
     * Do not expose database details to clients.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.error(
                "Database constraint violation. path={}",
                request.getRequestURI(),
                ex
        );

        return buildResponse(
                HttpStatus.CONFLICT,
                "DATA_INTEGRITY_VIOLATION",
                "The request conflicts with existing data",
                request.getRequestURI(),
                null
        );
    }


    /**
     * Final fallback handler.
     *
     * Never expose internal exception messages
     * in production.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        log.error(
                "Unexpected error. path={}",
                request.getRequestURI(),
                ex
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "An unexpected error occurred",
                request.getRequestURI(),
                null
        );
    }


    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String errorCode,
            String message,
            String path,
            List<ErrorDetail> errors) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        status.value(),
                        errorCode,
                        message,
                        path,
                        errors
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}