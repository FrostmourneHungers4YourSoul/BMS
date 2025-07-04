package com.example.bookmanagementservice.controller.impl;

import com.example.bookmanagementservice.controller.BookController;
import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.model.dto.response.ResponseMessage;
import com.example.bookmanagementservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Override
    public ResponseEntity<BookResponseDto> create(@RequestBody BookRequestDto book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @Override
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @Override
    public ResponseEntity<BookResponseDto> getBook(Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Override
    public ResponseEntity<BookResponseDto> update(Long id, @RequestBody BookRequestDto book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @Override
    public ResponseEntity<ResponseMessage> delete(Long id) {
        return ResponseEntity.ok(bookService.removeBook(id));
    }
}
