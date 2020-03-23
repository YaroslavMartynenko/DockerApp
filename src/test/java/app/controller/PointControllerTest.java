package app.controller;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    }

    @Test
    void shouldTrowEmptyListExceptionWhenListOfPointsIsEmpty() throws Exception {
        mockMvc.perform(get("/point"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
    }

    @Test
    @Sql(value = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnPoint() throws Exception {
        mockMvc.perform(get("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Point 1")))
                .andExpect(jsonPath("$.longtitude", is(1.0)))
                .andExpect(jsonPath("$.latitude", is(1.0)));
    }

    @Test
    void shouldThrowWrongIdExceptionWhenPointWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/point/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
    }

    @Test
    @Sql(value = {"/insert-test-points.sql", "/insert-test-attributes.sql", "/insert-test-values.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    }

    @Test
    void shouldThrowWrongIdExceptionIfPointWithSuchIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/point/show_point_attributes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("Object with such id is not found!")));
    }

    @Test
    @Sql(value = "/insert-test-points.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldThrowEmptyListExceptionWhenListOfAttributesIsEmpty() throws Exception {
        mockMvc.perform(get("/point/show_point_attributes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"))))
                .andExpect(content().string(containsString("No existing elements!")));
    }


    @Test
    void addNewPoint() {


    }

    @Test
    void addAttributeToPoint() {
    }

    @Test
    void deletePointById() {
    }
}
