package app.controller;

import app.domain.Point;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
class PointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final Point POINT = Point
            .builder()
            .id(1L)
            .longtitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
            .latitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
            .name("Point 1")
            .build();

    @Test
    @Sql(scripts = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnListOfPoints() throws Exception {
        mockMvc.perform(get("/point"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Point 1")))
                .andExpect(jsonPath("$[0].longtitude", is(1.0)))
                .andExpect(jsonPath("$[0].latitude", is(1.0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Point 2")))
                .andExpect(jsonPath("$[1].longtitude", is(2.0)))
                .andExpect(jsonPath("$[1].latitude", is(2.0)));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldTrowEmptyListExceptionWhenListOfPointsIsEmpty() throws Exception {
        mockMvc.perform(get("/point"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnPointById() throws Exception {
        mockMvc.perform(get("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Point 1")))
                .andExpect(jsonPath("$.longtitude", is(1.0)))
                .andExpect(jsonPath("$.latitude", is(1.0)));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionWhenPointWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = {"/insert-test-points.sql", "/insert-test-attributes.sql", "/insert-test-values.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnListOfAttributesThatContainsThisPoint() throws Exception {
        mockMvc.perform(get("/point/show_point_attributes/{id}", 1L))
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
    void shouldThrowWrongIdExceptionIfPointWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/point/show_point_attributes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(value = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowEmptyListExceptionWhenListOfAttributesIsEmpty() throws Exception {
        mockMvc.perform(get("/point/show_point_attributes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldSaveNewPoint() throws Exception {
        mockMvc.perform(post("/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(POINT)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Point 1")))
                .andExpect(jsonPath("$.longtitude", is(1.0)))
                .andExpect(jsonPath("$.latitude", is(1.0)));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowPointExistsExceptionWhenPointWithSuchCoordinatesAlreadyExists() throws Exception {
        mockMvc.perform(post("/point")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(POINT)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Point with such coordinates already exists!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = {"/insert-test-points.sql", "/insert-test-attributes.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldAddAttributeToPoint() throws Exception {
        mockMvc.perform(post("/point/add_attribute_to_point")
                .param("attributeId", "1")
                .param("pointId", "1")
                .param("value", "Some value"))
                .andDo(print())
                .andExpect(status().isOk());
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionIfPointWithSuchIdDoesNotExistInDb() throws Exception {
        mockMvc.perform(post("/point/add_attribute_to_point")
                .param("attributeId", "1")
                .param("pointId", "1")
                .param("value", "Some value"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowWrongIdExceptionIfAttributeWithSuchIdDoesNotExistInDb() throws Exception {
        mockMvc.perform(post("/point/add_attribute_to_point")
                .param("attributeId", "1")
                .param("pointId", "1")
                .param("value", "Some value"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    @Sql(scripts = {"/insert-test-points.sql", "/insert-test-attributes.sql", "/insert-test-values.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldThrowAttributePresentsExceptionWhenPointAlreadyContainsSuchAttribute() throws Exception {
        mockMvc.perform(post("/point/add_attribute_to_point")
                .param("attributeId", "1")
                .param("pointId", "1")
                .param("value", "Some value"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Such attribute already presents for this point!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }


    @Test
    @Sql(scripts = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeletePointById() throws Exception {
        mockMvc.perform(delete("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @Test
    void shouldThrowWrongIdExceptionWhenPointWithSuchIdDoesNotExistInDb() throws Exception {
        mockMvc.perform(delete("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
        assertTrue(TestTransaction.isFlaggedForRollback());
    }

    @AfterAll
    @Sql(scripts = "/delete-test-data.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    static void clearDatabase() {
    }
}
