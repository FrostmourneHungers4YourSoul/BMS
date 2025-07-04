package com.example.bookmanagementservice.controller;

import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorBooksResponseDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/authors")
@Tag(name = "Authors API", description = "Управление авторами")
public interface AuthorController {

    @Operation(summary = "Создать нового автора")
    @PostMapping
    ResponseEntity<AuthorResponseDto> create(
            @RequestBody(description = "Данные нового автора")
            @Valid AuthorRequestDto author);

    @Operation(summary = "Получить автора по ID")
    @ApiResponse(responseCode = "200", description = "Автор найден")
    @ApiResponse(responseCode = "404", description = "Автор не найден")
    @GetMapping("/{id}")
    ResponseEntity<AuthorBooksResponseDto> getAuthor(
            @Parameter(description = "ID автора")
            @PathVariable Long id);

    @Operation(summary = "Получить список авторов с пагинацией")
    @Parameters({
            @Parameter(name = "page", description = "Номер страницы (min = 0)", example = "0"),
            @Parameter(name = "size", description = "Количество элементов (default = 10)", example = "10"),
            @Parameter(name = "sort", description = "Сортировка (name,asc)", example = "name,asc")
    })
    @GetMapping
    ResponseEntity<Page<AuthorResponseDto>> getAuthors(
            @Parameter(hidden = true)
            @PageableDefault @NotNull Pageable pageable);
}
