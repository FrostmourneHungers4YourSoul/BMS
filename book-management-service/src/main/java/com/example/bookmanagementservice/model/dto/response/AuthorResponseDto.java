package com.example.bookmanagementservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными автора")
public record AuthorResponseDto
        (
                @Schema(description = "ID автора", example = "1")
                Long id,

                @Schema(description = "Автор", example = "Толстой Л.Н.")
                String name,

                @Schema(description = "Год рождения", example = "1828")
                @JsonProperty("birth_year")
                Integer birthYear
        ) {
}
