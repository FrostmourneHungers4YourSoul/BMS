package com.example.bookmanagementservice.controller;

import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorBooksResponseDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import com.example.bookmanagementservice.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping()
    public ResponseEntity<AuthorResponseDto> create(@RequestBody @Valid AuthorRequestDto author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorBooksResponseDto> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @GetMapping()
    public ResponseEntity<Page<AuthorResponseDto>> getAuthors(@PageableDefault @NotNull Pageable pageable) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageable));
    }
}
