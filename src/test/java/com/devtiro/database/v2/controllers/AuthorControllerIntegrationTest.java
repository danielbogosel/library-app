package com.devtiro.database.v2.controllers;

import com.devtiro.database.v2.domain.dto.AuthorDto;
import com.devtiro.database.v2.domain.entities.AuthorEntity;
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

import static com.devtiro.database.v2.TestDataUtil.createTestAuthorDtoA;
import static com.devtiro.database.v2.TestDataUtil.createTestAuthorEntityA;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatedAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto authorA = createTestAuthorDtoA();
        authorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatedAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto authorA = createTestAuthorDtoA();
        authorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").isNumber()
        );
    }
}
