package com.example.bookmanagementservice.model.dto.request;

import com.example.bookmanagementservice.model.Author;
import jakarta.validation.constraints.NotNull;

public record BookRequestDto
        (
                @NotNull String title,
                Author author,
                Integer year,
                String genre
        ) {
}
