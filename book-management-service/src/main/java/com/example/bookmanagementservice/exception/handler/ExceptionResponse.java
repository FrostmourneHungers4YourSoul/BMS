package com.example.bookmanagementservice.exception.handler;

public record ExceptionResponse
        (
                int statusCode,
                String error,
                String message
        ) {
}
