package com.example.bookmanagementservice.mapper;

import com.example.bookmanagementservice.model.Author;
import com.example.bookmanagementservice.model.dto.request.AuthorRequestDto;
import com.example.bookmanagementservice.model.dto.response.AuthorResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = BookMapper.class
)
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorRequestDto authorDto);

    AuthorResponseDto toDto(Author author);
}
