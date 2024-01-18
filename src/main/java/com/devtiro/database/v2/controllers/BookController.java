package com.devtiro.database.v2.controllers;

import com.devtiro.database.v2.domain.dto.BookDto;
import com.devtiro.database.v2.domain.entities.BookEntity;
import com.devtiro.database.v2.mappers.Mapper;
import com.devtiro.database.v2.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.saveBook(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.CREATED);

    }

    @GetMapping()
    public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAllBooks(pageable);
        return books.map(bookMapper::mapTo);

    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookEntity> foundBookEntity = bookService.findByIsbn(isbn);
        return foundBookEntity.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity partialUpdatedEntity = bookService.partialUpdateBookByIsbn(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(partialUpdatedEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBookByIsbn(@PathVariable String isbn) {
        bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
