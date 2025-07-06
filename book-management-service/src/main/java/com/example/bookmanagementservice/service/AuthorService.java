package com.example.bookmanagementservice.service;

import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Page<AuthorResponseDto> getAllAuthors(Pageable pageable);

    AuthorResponseDto createAuthor(AuthorRequestDto author);

    AuthorResponseDto getAuthor(Long id);

    Author getAuthorById(Long id);
}
