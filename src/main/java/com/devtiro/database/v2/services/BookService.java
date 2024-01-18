package com.devtiro.database.v2.services;

import com.devtiro.database.v2.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity saveBook(String isbn, BookEntity book);

    List<BookEntity> findAllBooks();

    Page<BookEntity> findAllBooks(Pageable pageable);

    Optional<BookEntity> findByIsbn(String isbn);

    BookEntity partialUpdateBookByIsbn(String isbn, BookEntity bookEntity);

    void deleteBookByIsbn(String isbn);
}
