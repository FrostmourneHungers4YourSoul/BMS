package com.example.bookmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AuthorRequestDto
        (
                @NotBlank String name,
                @JsonProperty("birth_year") Integer birthYear
        ) {
}
