package com.devtiro.database.v2.controllers;

import com.devtiro.database.v2.domain.dto.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.devtiro.database.v2.TestDataUtil.createTestBookDtoA;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatedBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto bookDto = createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/book/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatedBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto bookDto = createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").isString()
        );
    }

}
