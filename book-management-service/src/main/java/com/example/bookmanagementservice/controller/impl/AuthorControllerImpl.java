package com.example.bookmanagementservice.controller.impl;

import com.example.bookmanagementservice.controller.AuthorController;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorBooksResponseDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import com.example.bookmanagementservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    @Override
    public ResponseEntity<AuthorResponseDto> create(@RequestBody AuthorRequestDto author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @Override
    public ResponseEntity<AuthorBooksResponseDto> getAuthor(Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @Override
    public ResponseEntity<Page<AuthorResponseDto>> getAuthors(Pageable pageable) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageable));
    }
}
