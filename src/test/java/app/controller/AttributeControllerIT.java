package app.controller;

import app.domain.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@Transactional
class AttributeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final Attribute ATTRIBUTE = Attribute
            .builder()
            .id(1L)
            .name("Attribute 1")
            .build();

    @Test
    @Sql(scripts = "/insert-test-attributes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldreturnListOfAttributes() throws Exception {
        mockMvc.perform(get("/attribute"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Attribute 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Attribute 2")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldTrowEmptyListExceptionWhenListOfAttributesIsEmpty() throws Exception {
        mockMvc.perform(get("/attribute"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = "/insert-test-attributes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnAttributeById() throws Exception {
        mockMvc.perform(get("/attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Attribute 1")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionWhenAttributeWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = {"/insert-test-points.sql", "/insert-test-attributes.sql", "/insert-test-values.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnListOfPointsThatContainSuchAttribute() throws Exception {
        mockMvc.perform(get("/attribute/show_points_with_attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Point 1")))
                .andExpect(jsonPath("$[0].longtitude", is(1.0)))
                .andExpect(jsonPath("$[0].latitude", is(1.0)));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionIfAttributeWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/attribute/show_points_with_attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = "/insert-test-attributes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowEmptyListExceptionWhenListOfAttributesIsEmpty() throws Exception {
        mockMvc.perform(get("/attribute/show_points_with_attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldSaveNewAttribute() throws Exception {
        mockMvc.perform(post("/attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(ATTRIBUTE)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Attribute 1")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = "/insert-test-attributes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowAttributeExistsExceptionWhenAttributeWithSuchNameAlreadyExists() throws Exception {
        mockMvc.perform(post("/attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(ATTRIBUTE)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Attribute with such name already exists!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = "/insert-test-attributes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeletePointById() throws Exception {
        mockMvc.perform(delete("/attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionWhenAttributeWithSuchIdDoesNotExistInDb() throws Exception {
        mockMvc.perform(delete("/attribute/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @AfterAll
    @Sql(value = "/delete-test-data.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    static void clearDatabase() {
    }
}