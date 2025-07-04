package com.example.bookmanagementservice.controller;

import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.model.dto.response.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Books API", description = "Управление книгами")
@RequestMapping("/books")
public interface BookController {

    @Operation(summary = "Создать новую книгу")
    @PostMapping
    ResponseEntity<BookResponseDto> create(
            @RequestBody(description = "Данные новой книги")
            @Valid BookRequestDto book);

    @Operation(summary = "Получить список книг")
    @GetMapping
    ResponseEntity<List<BookResponseDto>> getBooks();

    @Operation(summary = "Получить книгу по ID")
    @ApiResponse(responseCode = "200", description = "Книга найдена")
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @GetMapping("/{id}")
    ResponseEntity<BookResponseDto> getBook(
            @Parameter(description = "ID книги")
            @PathVariable Long id);

    @Operation(summary = "Обновить информацию о книге")
    @PutMapping("/{id}")
    ResponseEntity<BookResponseDto> update(
            @Parameter(description = "ID книги")
            @PathVariable Long id,
            @RequestBody(description = "Новые данные книги")
            @Valid BookRequestDto book);

    @Operation(summary = "Удалить книгу")
    @ApiResponse(responseCode = "200", description = "Книга удалена")
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessage> delete(
            @Parameter(description = "ID книги")
            @PathVariable Long id);
}
