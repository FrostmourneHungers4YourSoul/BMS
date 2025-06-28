package com.example.bookmanagementservice.service.impl;

import com.example.bookmanagementservice.exception.ResourceAlreadyExistsException;
import com.example.bookmanagementservice.exception.ResourceNotFoundException;
import com.example.bookmanagementservice.mapper.BookMapper;
import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.Book;
import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.model.dto.response.ResponseMessage;
import com.example.bookmanagementservice.repository.BookRepository;
import com.example.bookmanagementservice.service.AuthorService;
import com.example.bookmanagementservice.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final AuthorService authorService;
    private final BookMapper mapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    @Override
    public BookResponseDto createBook(BookRequestDto requestDto) {
        Book newBook = mapper.toEntity(requestDto);

        log.info("Request: {}", requestDto);
        log.info("If: {}", requestDto.authorId() != null);
        if (requestDto.authorId() != null) {
            Author author = authorService.getAuthorById(requestDto.authorId());
            newBook.setAuthor(author);
        }
        try {
            Book book = repository.save(newBook);
            log.info("Saved book: {}", book);
            return mapper.toDto(book);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate book title: {}", requestDto.title());
            throw new ResourceAlreadyExistsException("Book with name is already exists: " + requestDto.title());
        }
    }

    @Override
    public List<BookResponseDto> getBooks() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found."));
        log.info("Found book: {}", book);
        return mapper.toDto(book);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto requestDto) {
        if (!repository.existsById(id))
            throw new ResourceNotFoundException("Book not found.");

        Author author = authorService.getAuthorById(requestDto.authorId());

        Book updatingBook = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found."));

        Book updatedBook = Book.builder()
                .id(updatingBook.getId())
                .title(requestDto.title())
                .author(author)
                .genre(requestDto.genre())
                .year(requestDto.year())
                .build();

        Book book = repository.save(updatedBook);
        log.info("Book was updated: {}", book);
        return mapper.toDto(book);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    @Override
    public ResponseMessage removeBook(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found."));
        repository.removeById(id);
        log.info("Has been removed: {}", book.getTitle());
        return new ResponseMessage("Book '" + book.getTitle() + "' has been removed.");
    }
}
