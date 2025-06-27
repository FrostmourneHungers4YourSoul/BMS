package com.example.bookmanagementservice.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthorRequestDto
        (
                @NotNull String name,
                Integer birthYear
        ) {
}
