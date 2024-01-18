package com.devtiro.database.v2.services.impl;

import com.devtiro.database.v2.domain.entities.BookEntity;
import com.devtiro.database.v2.repositories.BookRepository;
import com.devtiro.database.v2.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookEntity saveBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return repository.save(book);
    }

    @Override
    public List<BookEntity> findAllBooks() {
        return (List<BookEntity>) repository.findAll();
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return repository.findById(isbn);
    }

    @Override
    public Page<BookEntity> findAllBooks(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public BookEntity partialUpdateBookByIsbn(String isbn, BookEntity bookEntity) {
        Optional<BookEntity> searchedBook = findByIsbn(isbn);
        if (searchedBook.isPresent()) {
            BookEntity updatedBook = searchedBook.get();
            updatedBook.setTitle(Optional.ofNullable(bookEntity.getTitle()).orElse(updatedBook.getTitle()));
            return saveBook(isbn, updatedBook);
        } else {
            throw new RuntimeException("Book does not exist");
        }

    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        Optional<BookEntity> bookEntity = findByIsbn(isbn);
        if (bookEntity.isPresent()) {
            repository.deleteById(isbn);
        } else {
            System.out.println("No book with isbn: " + isbn);
        }
    }
}
