package com.example.bookmanagementservice.service;

import com.example.bookmanagementservice.BaseUnitTest;
import com.example.bookmanagementservice.exception.ResourceAlreadyExistsException;
import com.example.bookmanagementservice.exception.ResourceNotFoundException;
import com.example.bookmanagementservice.mapper.BookMapper;
import com.example.bookmanagementservice.model.Book;
import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;
import com.example.bookmanagementservice.repository.BookRepository;
import com.example.bookmanagementservice.service.impl.AuthorServiceImpl;
import com.example.bookmanagementservice.service.impl.BookServiceImpl;
import com.example.bookmanagementservice.util.AuthorUtil;
import com.example.bookmanagementservice.util.BookUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Slf4j
class BookServiceImplTest extends BaseUnitTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper mapper;

    @Mock
    private AuthorServiceImpl authorServiceImpl;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    @DisplayName("Успешно создает книгу")
    void testSuccessfullyCreateBook() {
        //given
        BookRequestDto requestBook = BookUtil.getBookRequest();

        when(mapper.toEntity(any(BookRequestDto.class))).thenReturn(BookUtil.getBookTransient());
        when(repository.save(any(Book.class))).thenReturn(BookUtil.getBookPersisted());
        when(mapper.toDto(any(Book.class))).thenReturn(BookUtil.getBookResponse());

        //when
        BookResponseDto result = service.createBook(requestBook);

        //then
        assertNotNull(result);
        assertEquals(99L, result.authorId());

        verify(mapper).toEntity(any(BookRequestDto.class));
        verify(authorServiceImpl).getAuthorById(anyLong());
        verify(repository).save(any(Book.class));
        verify(mapper).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Должен выбросить исключение дубликата: ResourceAlreadyExistsException")
    void testThrowWhenCreatingDuplicateBookTitle() {
        //given
        BookRequestDto requestBook = BookUtil.getBookRequest();

        String expectedMessage = "Book with name is already exists: " + requestBook.title();
        when(repository.existsByTitle(requestBook.title())).thenReturn(true);

        //when
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class, () -> service.createBook(requestBook)
        );

        //then
        assertEquals(expectedMessage, exception.getMessage());

        verify(mapper, never()).toEntity(any(BookRequestDto.class));
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Возвращает все книги")
    void testSuccessfullyGetAllBooks() {
        //given
        when(repository.findAll()).thenReturn(List.of(BookUtil.getBookPersisted()));
        when(mapper.toDto(any(Book.class))).thenReturn(BookUtil.getBookResponse());

        //when
        List<BookResponseDto> result = service.getBooks();

        //then
        assertEquals("Высоконагруженные приложения.", result.get(0).title());
        assertEquals(1, result.size());

        verify(repository).findAll();
        verify(mapper, times(1)).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Успешно находит книгу по id")
    void testSuccessfullyGetBookById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(BookUtil.getBookPersisted()));
        when(mapper.toDto(any(Book.class))).thenReturn(BookUtil.getBookResponse());

        //when
        BookResponseDto result = service.getBook(99L);

        //then
        assertNotNull(result);

        verify(repository, times(1)).findById(anyLong());
        verify(mapper).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Должен выбросить исключение когда книга не найдена: ResourceNotFoundException")
    void testThrowWhenGettingBookById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> service.getBook(99L));

        //then
        assertEquals("Book not found.", exception.getMessage());

        verify(mapper, never()).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Успешно обновляет данные книг")
    void testSuccessfullyUpdateBook() {
        //given
        Long id = 51L;
        String title = "Надежные, масштабируемые и удобные в сопровождении приложения.";
        BookRequestDto requestBook = BookUtil.getBookRequest();
        Book updatedBook = BookUtil.getBookPersisted();
        updatedBook.setTitle(title);
        updatedBook.setYear(2018);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.ofNullable(BookUtil.getBookPersisted()));
        when(authorServiceImpl.getAuthorById(99L)).thenReturn(AuthorUtil.getAuthorPersisted());
        when(repository.save(any(Book.class))).thenReturn(updatedBook);
        when(mapper.toDto(updatedBook)).thenReturn(BookUtil.getUpdatedBook());

        //when
        BookResponseDto result = service.updateBook(id, requestBook);

        //then
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(title, result.title());

        verify(authorServiceImpl).getAuthorById(anyLong());
        verify(repository).save(any(Book.class));
        verify(mapper).toDto(any(Book.class));
    }

    @Test
    @DisplayName("Успешно удаляет книгу по id")
    void testRemoveBookByIdWhenFound() {
        //given
        Long id = 51L;
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(BookUtil.getBookPersisted()));

        //when
        service.removeBook(id);

        //then
        verify(repository).findById(anyLong());
        verify(repository).removeById(anyLong());
    }
}