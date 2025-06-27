package com.example.bookmanagementservice.service.impl;

import com.example.bookmanagementservice.repository.BookRepository;
import com.example.bookmanagementservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

}
