package com.devtiro.database.v2.repositories;

import com.devtiro.database.v2.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.devtiro.database.v2.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//Curata contextul pentru fiecare test in parte. De ex o data salvata o inregistrare in db de la un test precedent, aceasta sa nu mai existe la urmatorul test.
public class AuthorEntityRepositoryIntegrationTest {
    private AuthorRepository underTest;


    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = createTestAuthorEntityA();
        underTest.save(authorEntity);

        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = createTestAuthorEntityA();
        AuthorEntity authorEntityB = createTestAuthorEntityB();
        AuthorEntity authorEntityC = createTestAuthorEntityC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).contains(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatAnAuthorCanBeUpdated() {
        AuthorEntity authorEntityA = createTestAuthorEntityA();
        underTest.save(authorEntityA);
        authorEntityA.setName("UPDATED");
        underTest.save(authorEntityA);
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        result.isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
    }

    @Test
    public void testThatAnAuthorCanBeDeleted() {
        AuthorEntity authorEntityA = createTestAuthorEntityA();
        underTest.save(authorEntityA);
        underTest.deleteById(authorEntityA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLesThen() {
        AuthorEntity authorEntityA = createTestAuthorEntityA();
        AuthorEntity authorEntityB = createTestAuthorEntityB();
        AuthorEntity authorEntityC = createTestAuthorEntityC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result).containsExactly(authorEntityA, authorEntityB);
    }

    @Test
    public void  testThatGetAuthorsWithAgeGreaterThen(){
        AuthorEntity authorEntityA = createTestAuthorEntityA();
        AuthorEntity authorEntityB = createTestAuthorEntityB();
        AuthorEntity authorEntityC = createTestAuthorEntityC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).containsExactly(authorEntityC);

    }
}
