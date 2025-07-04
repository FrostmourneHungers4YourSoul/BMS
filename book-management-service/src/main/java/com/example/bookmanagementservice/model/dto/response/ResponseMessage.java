package com.example.bookmanagementservice.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Record для сообщения")
public record ResponseMessage
        (
                @Schema(description = "Сообщение",
                        example = "Книга успешно удалена")
                String message
        ) {
}
