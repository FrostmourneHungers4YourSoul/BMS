package com.example.bookmanagementservice.service;

import com.example.bookmanagementservice.BaseUnitTest;
import com.example.bookmanagementservice.exception.ResourceAlreadyExistsException;
import com.example.bookmanagementservice.exception.ResourceNotFoundException;
import com.example.bookmanagementservice.mapper.AuthorMapper;
import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import com.example.bookmanagementservice.repository.AuthorRepository;
import com.example.bookmanagementservice.service.impl.AuthorServiceImpl;
import com.example.bookmanagementservice.util.AuthorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

        AuthorResponseDto dto1 = new AuthorResponseDto(1L, "Толстой Л.Н.", 1828);
        AuthorResponseDto dto2 = new AuthorResponseDto(2L, "Достоевский Ф.М.", 1821);

        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2), pageable, 2);

        when(repository.findAll(pageable)).thenReturn(authorPage);
        when(mapper.toDto(author1)).thenReturn(dto1);
        when(mapper.toDto(author2)).thenReturn(dto2);

        Page<AuthorResponseDto> result = service.getAllAuthors(pageable);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();

        assertThat(result.getContent())
                .hasSize(2)
                .containsExactly(dto1, dto2);

        verify(repository).findAll(pageable);
        verify(mapper, times(2)).toDto(any(Author.class));
    }

    @Test
    @DisplayName("Успешно находит автора по id")
    void testSuccessfullyGetAuthorById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(AuthorUtil.getAuthorPersisted()));
        when(mapper.toDto(any(Author.class))).thenReturn(AuthorUtil.getAuthorResponse());

        //when
        AuthorResponseDto result = service.getAuthor(99L);

        //then
        assertNotNull(result);
        assertEquals("Martin K.", result.name());

        verify(repository).findById(anyLong());
        verify(mapper).toDto(any(Author.class));
    }

    @Test
    @DisplayName("Успешно создает автора")
    void testSuccessfullyCreateAuthor() {
        //given
        AuthorResponseDto responseDto = AuthorUtil.getAuthorResponse();

        when(mapper.toEntity(any(AuthorRequestDto.class))).thenReturn(AuthorUtil.getAuthorTransient());
        when(repository.save(any(Author.class))).thenReturn(AuthorUtil.getAuthorPersisted());
        when(mapper.toDto(any(Author.class))).thenReturn(responseDto);

        //when
        AuthorResponseDto result = service.createAuthor(AuthorUtil.getAuthorRequest());

        //than
        assertNotNull(result);
        assertEquals(99L, result.id());
        assertEquals(responseDto, result);

        verify(mapper).toEntity(any(AuthorRequestDto.class));
        verify(repository).save(any(Author.class));
        verify(mapper).toDto(any(Author.class));
    }

    @Test
    @DisplayName("Должен выбросить исключение когда пользователь не найден: ResourceNotFoundException")
    void testThrowWhenGettingAuthorById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> service.getAuthor(1L)
        );

        //then
        assertEquals("Author not found.", exception.getMessage());

        verify(repository).findById(anyLong());
        verify(mapper, never()).toDto(any(Author.class));
    }

    @Test
    @DisplayName("Должен выбросить исключение дубликата: ResourceAlreadyExistsException")
    void testThrowWhenCreatingDuplicateAuthor() {
        //given
        AuthorRequestDto requestDto = AuthorUtil.getAuthorRequest();

        when(repository.existsByName(anyString())).thenReturn(true);

        //when
        var exception = assertThrows(
                ResourceAlreadyExistsException.class, () -> service.createAuthor(requestDto)
        );

        //than
        assertEquals("Author [Martin K.] already exists.", exception.getMessage());

        verify(mapper, never()).toEntity(any(AuthorRequestDto.class));
        verify(repository, never()).save(any(Author.class));
    }

    @Test
    @DisplayName("Возвращает автора из репозитория по id")
    void testSuccessfullyGetAuthorByIdFromRepository() {
        //given
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(AuthorUtil.getAuthorPersisted()));

        //when
        Author result = service.getAuthorById(99L);

        //than
        assertNotNull(result);

        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}
