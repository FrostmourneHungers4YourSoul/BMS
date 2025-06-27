package com.example.bookmanagementservice.model.dto.response;

public record BookResponseDto
        (
                Long id,
                String title,
                Integer year,
                String genre
        ) {
}
