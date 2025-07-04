package com.example.bookmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на создание/обновление автора")
public record AuthorRequestDto
        (
                @Schema(description = "Автор",
                        example = "Толстой Л.Н.",
                        requiredMode = Schema.RequiredMode.AUTO)
                @NotBlank
                String name,

                @Schema(description = "Год рождения автора", example = "1828")
                @JsonProperty("birth_year")
                Integer birthYear
        ) {
}
