package com.shubham.api.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private String error;
    private String timestamp;

    // Required by Jackson
    public ApiResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    // Main constructor
    public ApiResponse(boolean success, T data, String message, String error) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Success factory
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }

    // Error factory
    public static <T> ApiResponse<T> error(String error, String message) {
        return new ApiResponse<>(false, null, message, error);
    }

}
