package com.example.bookmanagementservice.service.impl;

import com.example.bookmanagementservice.exception.ResourceAlreadyExistsException;
import com.example.bookmanagementservice.exception.ResourceNotFoundException;
import com.example.bookmanagementservice.mapper.AuthorMapper;
import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import com.example.bookmanagementservice.repository.AuthorRepository;
import com.example.bookmanagementservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Override
    public Page<AuthorResponseDto> getAllAuthors(Pageable pageable) {
        Page<Author> authors = repository.findAll(pageable);
        log.info("Page authors: {}", authors.getTotalElements());

        return authors.map(mapper::toDto);
    }

    @Override
    public AuthorResponseDto getAuthor(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found."));
        log.info("Found author: {}", author);
        return mapper.toDto(author);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto author) {
        log.info("Request author: {}", author);

        if (repository.existsByName(author.name())) {
            log.warn("Duplicate author name: {}", author.name());
            throw new ResourceAlreadyExistsException("Author [" + author.name() + "] already exists.");
        }
        Author newAuthor = mapper.toEntity(author);

        Author savedAuthor = repository.save(newAuthor);
        log.info("Saved author: {}", savedAuthor);

        return mapper.toDto(savedAuthor);
    }

    @Override
    public Author getAuthorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found."));
    }
}
