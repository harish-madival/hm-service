package com.hotel.auth.model;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse {

    private final String status;
    private final Object data;

    public ApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
