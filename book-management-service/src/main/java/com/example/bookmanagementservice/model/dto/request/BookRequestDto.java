package com.example.bookmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record BookRequestDto
        (
                @NotBlank String title,
                @JsonProperty("author_id") Long authorId,
                Integer year,
                String genre
        ) {
}
