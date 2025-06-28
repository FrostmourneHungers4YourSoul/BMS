package com.example.bookmanagementservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookResponseDto
        (
                Long id,
                String title,
                @JsonProperty("author_id") Long authorId,
                Integer year,
                String genre
        ) {
}
