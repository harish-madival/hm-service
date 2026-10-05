package com.harish.gateway.dto;

public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String correlationId;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, String error, String correlationId) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.correlationId =correlationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}