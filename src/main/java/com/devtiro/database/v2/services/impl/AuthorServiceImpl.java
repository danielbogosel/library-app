package com.devtiro.database.v2.services.impl;

import com.devtiro.database.v2.domain.entities.AuthorEntity;
import com.devtiro.database.v2.repositories.AuthorRepository;
import com.devtiro.database.v2.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAllAuthors() {
        return (List<AuthorEntity>) authorRepository.findAll();
    }

    @Override
    public Optional<AuthorEntity> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public AuthorEntity fullUpdate(AuthorEntity authorEntity, Long id) {
        Optional<AuthorEntity> searchedAuthorEntity = findAuthorById(id);
        if (searchedAuthorEntity.isPresent()) {
            authorEntity.setId(id);
            saveAuthor(authorEntity);
        } else {
            System.out.println("User not found");
        }
        return searchedAuthorEntity.get();
    }

    @Override
    public AuthorEntity partialUpdate(AuthorEntity authorEntity, Long id) {
        Optional<AuthorEntity> searchedAuthorEntity = findAuthorById(id);
        if (searchedAuthorEntity.isPresent()) {
            AuthorEntity authorUpdated = searchedAuthorEntity.get();
            authorUpdated.setName(Optional.ofNullable(authorEntity.getName()).orElse(authorUpdated.getName()));
            authorUpdated.setAge(Optional.ofNullable(authorEntity.getAge()).orElse(authorUpdated.getAge()));
            return authorRepository.save(authorUpdated);
        } else {
            throw new RuntimeException("Author does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<AuthorEntity> author = findAuthorById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
        } else {
            System.out.println("No author for given ID");
        }
    }
}
