package com.example.bookmanagementservice.util;

import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;

public class AuthorUtil {
    public static Author getAuthorTransient() {
        return Author.builder()
                .name("Martin K.")
                .birthYear(1982)
                .build();
    }

    public static Author getAuthorPersisted() {
        return Author.builder()
                .id(99L)
                .name("Martin K.")
                .birthYear(1982)
                .build();
    }

    public static AuthorRequestDto getAuthorRequest() {
        return new AuthorRequestDto("Martin K.", 1982);
    }

    public static AuthorResponseDto getAuthorResponse() {
        return new AuthorResponseDto(99L, "Martin K.", 1982);
    }
}
