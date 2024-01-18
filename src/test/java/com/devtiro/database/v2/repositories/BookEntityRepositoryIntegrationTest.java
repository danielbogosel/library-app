package com.devtiro.database.v2.repositories;

import com.devtiro.database.v2.TestDataUtil;
import com.devtiro.database.v2.domain.entities.AuthorEntity;
import com.devtiro.database.v2.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTest {

    private BookRepository underTest;


    @Autowired
    public BookEntityRepositoryIntegrationTest(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBokCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(authorEntity);
        underTest.save(bookEntity);
        bookEntity.setAuthorEntity(authorEntity);


        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatManyBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorEntityB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorEntityC();
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntityA);
        BookEntity bookEntityB = TestDataUtil.createTestBookEntityB(authorEntityB);
        BookEntity bookEntityC = TestDataUtil.createTestBookEntityC(authorEntityC);

        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).contains(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatABookCanBeUpdated() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();

        BookEntity bookEntityA = TestDataUtil.createTestBookEntityB(authorEntityA);
        bookEntityA.setAuthorEntity(authorEntityA);
        underTest.save(bookEntityA);

        bookEntityA.setTitle("UPDATED");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testThatABookCanBwDeleted() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();

        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA(authorEntityA);
        bookEntityA.setAuthorEntity(authorEntityA);
        underTest.save(bookEntityA);

        underTest.deleteById(bookEntityA.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isEmpty();


    }
}
