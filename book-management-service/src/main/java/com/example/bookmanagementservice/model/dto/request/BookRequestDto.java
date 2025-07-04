package com.example.bookmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на создание/обновление книги")
public record BookRequestDto
        (
                @Schema(description = "Название книги",
                        example = "Война и мир",
                        requiredMode = Schema.RequiredMode.AUTO)
                @NotBlank
                String title,

                @Schema(description = "ID автора",
                        example = "1")
                @JsonProperty("author_id")
                Long authorId,

                @Schema(description = "Год публикации", example = "1869")
                Integer year,

                @Schema(description = "Жанр книги", example = "Роман")
                String genre
        ) {
}
