package com.example.bookmanagementservice.util;

import com.example.bookmanagementservice.model.Book;
import com.example.bookmanagementservice.model.dto.request.BookRequestDto;
import com.example.bookmanagementservice.model.dto.response.BookResponseDto;

public class BookUtil {

    public static Book getBookTransient() {
        return Book.builder()
                .title("Высоконагруженные приложения.")
                .author(AuthorUtil.getAuthorPersisted())
                .year(2017)
                .genre("Техническая литература")
                .build();
    }

    public static Book getBookPersisted() {
        return Book.builder()
                .id(51L)
                .title("Высоконагруженные приложения.")
                .author(AuthorUtil.getAuthorPersisted())
                .year(2017)
                .genre("Техническая литература")
                .build();
    }

    public static BookRequestDto getBookRequest() {
        return new BookRequestDto(
                "Высоконагруженные приложения.",
                99L,
                2017,
                "Техническая литература");
    }

    public static BookResponseDto getBookResponse() {
        return new BookResponseDto(
                51L,
                "Высоконагруженные приложения.",
                99L,
                2017,
                "Техническая литература");
    }

    public static BookResponseDto getUpdatedBook() {
        return new BookResponseDto(
                51L,
                "Надежные, масштабируемые и удобные в сопровождении приложения.",
                99L,
                2018,
                "Техническая литература");
    }
}
