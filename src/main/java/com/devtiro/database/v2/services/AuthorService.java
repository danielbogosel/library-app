package com.devtiro.database.v2.services;

import com.devtiro.database.v2.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity saveAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAllAuthors();

    Optional<AuthorEntity> findAuthorById(Long id);

    AuthorEntity fullUpdate(AuthorEntity authorEntity, Long id);

    AuthorEntity partialUpdate(AuthorEntity authorEntity, Long id);

    void delete(Long id);
}
