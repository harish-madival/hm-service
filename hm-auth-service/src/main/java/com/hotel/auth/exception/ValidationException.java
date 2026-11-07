package com.hotel.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ValidationException extends RuntimeException {

    private HttpStatus status;

    public ValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ValidationException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

}
