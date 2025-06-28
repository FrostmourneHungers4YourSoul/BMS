package com.example.bookmanagementservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthorBooksResponseDto
        (
                Long id,
                String name,
                @JsonProperty("birth_year") Integer birthYear,
                List<BookResponseDto> books
        ) {
}
