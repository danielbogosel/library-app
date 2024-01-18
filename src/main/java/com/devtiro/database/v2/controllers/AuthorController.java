package com.devtiro.database.v2.controllers;

import com.devtiro.database.v2.domain.dto.AuthorDto;
import com.devtiro.database.v2.domain.entities.AuthorEntity;
import com.devtiro.database.v2.mappers.Mapper;
import com.devtiro.database.v2.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> creatAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorEntity> authors = authorService.findAllAuthors();
        return new ResponseEntity<>(authors.stream()
                .map(authorMapper::mapTo)
                .collect(toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthorEntity = authorService.findAuthorById(id);
        return foundAuthorEntity.map(author -> {
                    AuthorDto authorDto = authorMapper.mapTo(author);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.fullUpdate(authorEntity, id);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity partialUpdatedAuthorEntity = authorService.partialUpdate(authorEntity, id);
        return new ResponseEntity<>(authorMapper.mapTo(partialUpdatedAuthorEntity), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthorById(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
