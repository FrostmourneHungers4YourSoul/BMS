package com.example.bookmanagementservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с информацией о книге")
public record BookResponseDto
        (
                @Schema(description = "ID книги", example = "9")
                Long id,

                @Schema(description = "Название книги", example = "Война и мир")
                String title,

                @Schema(description = "ID автора", example = "3")
                @JsonProperty("author_id") Long authorId,

                @Schema(description = "Год публикации", example = "1869")
                Integer year,

                @Schema(description = "Жанр книги", example = "Роман-эпопея")
                String genre
        ) {
}
