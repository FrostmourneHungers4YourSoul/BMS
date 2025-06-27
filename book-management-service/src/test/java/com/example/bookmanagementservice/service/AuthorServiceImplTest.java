package com.example.bookmanagementservice.service;

import com.example.bookmanagementservice.BaseUnitTest;
import com.example.bookmanagementservice.exception.ResourceAlreadyExistsException;
import com.example.bookmanagementservice.exception.ResourceNotFoundException;
import com.example.bookmanagementservice.mapper.AuthorMapper;
import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.repository.AuthorRepository;
import com.example.bookmanagementservice.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AuthorServiceImplTest extends BaseUnitTest {

    @Mock
    private AuthorRepository repository;
    @Mock
    private AuthorMapper mapper;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    @DisplayName("Возвращает страницу авторов")
    void testSuccessfullyGetAllAuthors() {
        Pageable pageable = PageRequest.of(0, 2);

        Author author1 = Author.builder()
                .id(1L)
                .name("Толстой Л.Н.")
                .birthYear(1828)
                .build();

        Author author2 = Author.builder()
                .id(2L)
                .name("Достоевский Ф.М.")
                .birthYear(1821)
                .build();

        AuthorResponseDto dto1 = new AuthorResponseDto(1L, "Толстой Л.Н.", 1828, List.of());
        AuthorResponseDto dto2 = new AuthorResponseDto(2L, "Достоевский Ф.М.", 1821, List.of());

        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2), pageable, 2);

        when(repository.findAll(pageable)).thenReturn(authorPage);
        when(mapper.toDto(author1)).thenReturn(dto1);
        when(mapper.toDto(author2)).thenReturn(dto2);

        Page<AuthorResponseDto> result = service.getAllAuthors(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(List.of(dto1, dto2), result.getContent());

        verify(repository).findAll(pageable);
        verify(mapper).toDto(author1);
        verify(mapper).toDto(author2);
    }

    @Test
    @DisplayName("Успешно находит автора по id")
    void testSuccessfullyGetAuthorById() {
        Long id = 1L;
        BookResponseDto bookDto = new BookResponseDto(id, "Высоконагруженные приложения.", 2017, "Техническая литература");
        List<BookResponseDto> books = List.of(bookDto);

        Author author = Author.builder()
                .id(id)
                .name("Martin K.")
                .birthYear(1982)
                .build();

        AuthorResponseDto authorDto = new AuthorResponseDto(author.getId(), author.getName(), author.getBirthYear(), books);

        when(repository.findById(id)).thenReturn(Optional.of(author));
        when(mapper.toDto(author)).thenReturn(authorDto);

        AuthorResponseDto result = service.getAuthor(id);

        assertNotNull(result);
        assertEquals(authorDto, result);

        verify(repository).findById(1L);
        verify(mapper, times(1)).toDto(author);
    }

    @Test
    @DisplayName("Успешно создает автора")
    void testSuccessfullyCreateAuthor() {
        Long id = 1L;
        AuthorRequestDto requestDto = new AuthorRequestDto("Martin K.", 1982);

        Author savedAuthor = Author.builder()
                .id(id)
                .name("Martin K.")
                .birthYear(1982)
                .build();

        BookResponseDto bookDto = new BookResponseDto(id, "Высоконагруженные приложения.", 2017, "Техническая литература");
        List<BookResponseDto> books = List.of(bookDto);

        AuthorResponseDto responseDto = new AuthorResponseDto(savedAuthor.getId(), savedAuthor.getName(), savedAuthor.getBirthYear(), books);

        Author mappedAuthor = Author.builder()
                .name("Martin K.")
                .birthYear(1982)
                .build();

        when(mapper.toEntity(requestDto)).thenReturn(mappedAuthor);
        when(repository.save(mappedAuthor)).thenReturn(savedAuthor);
        when(mapper.toDto(savedAuthor)).thenReturn(responseDto);

        AuthorResponseDto result = service.createAuthor(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(responseDto, result);

        verify(mapper).toEntity(requestDto);
        verify(repository).save(mappedAuthor);
        verify(mapper).toDto(savedAuthor);
    }

    @Test
    @DisplayName("Должен выбросить исключение пользователь не найден: ResourceNotFoundException")
    void testThrowWhenGettingAuthorById() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getAuthor(1L);
        });

        assertEquals("Author not found", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Должен выбросить исключение дубликата: ResourceAlreadyExistsException")
    void testThrowWhenCreatingDuplicateAuthor() {
        AuthorRequestDto requestDto = new AuthorRequestDto("Толстой Л.Н.", 1828);
        Author author = Author.builder()
                .name("Толстой Л.Н.")
                .birthYear(1828)
                .build();

        when(mapper.toEntity(requestDto)).thenReturn(author);
        when(repository.save(author))
                .thenThrow(new DataIntegrityViolationException("Duplicate author name"));

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            service.createAuthor(requestDto);
        });

        assertEquals("Author with name already exists: " + author.getName(), exception.getMessage());

        verify(mapper).toEntity(requestDto);
        verify(repository).save(author);
    }
}
