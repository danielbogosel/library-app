package com.devtiro.database.v2;

import com.devtiro.database.v2.domain.dto.AuthorDto;
import com.devtiro.database.v2.domain.dto.BookDto;
import com.devtiro.database.v2.domain.entities.AuthorEntity;
import com.devtiro.database.v2.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }


    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Karl May")
                .age(45)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Mihai Eminescu")
                .age(38)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Arthur Conan Doyle")
                .age(65)
                .build();
    }

    public static BookEntity createTestBookEntityA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1213")
                .title("Winnetou")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookEntityB(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1214")
                .title("Poezii naturale")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookEntityC(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1217")
                .title("Aventurile lui Sherlock Holmes")
                .authorEntity(authorEntity)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("Karl May")
                .age(45)
                .build();
    }

    public static BookDto createTestBookDtoA(AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("1213")
                .title("Winnetou")
                .author(authorDto)
                .build();
    }
}
