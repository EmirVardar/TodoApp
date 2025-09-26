package com.example.todoapp.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(status, message, path);
    }
}