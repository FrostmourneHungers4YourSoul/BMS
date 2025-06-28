package com.example.bookmanagementservice.controller;

import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.model.dto.response.ResponseMessage;
import com.example.bookmanagementservice.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping()
    public ResponseEntity<BookResponseDto> create(@RequestBody @Valid BookRequestDto book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @GetMapping()
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(@PathVariable Long id, @RequestBody @Valid BookRequestDto book){
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.removeBook(id));
    }
}
