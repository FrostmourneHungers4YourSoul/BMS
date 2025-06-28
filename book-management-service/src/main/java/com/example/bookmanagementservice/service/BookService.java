package com.example.bookmanagementservice.service;

import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.model.dto.response.ResponseMessage;

import java.util.List;

public interface BookService {
    BookResponseDto createBook(BookRequestDto book);

    List<BookResponseDto> getBooks();

    BookResponseDto getBook(Long id);

    BookResponseDto updateBook(Long id, BookRequestDto book);

    ResponseMessage removeBook(Long id);
}
