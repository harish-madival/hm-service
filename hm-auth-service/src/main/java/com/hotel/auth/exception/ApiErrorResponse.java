package com.hotel.auth.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ApiErrorResponse {

    private Instant timestamp;

    private int status;

    private String errorCode;

    private String message;

    private String path;

    private List<ErrorDetail> errors;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
            Instant timestamp,
            int status,
            String errorCode,
            String message,
            String path,
            List<ErrorDetail> errors) {

        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

}
