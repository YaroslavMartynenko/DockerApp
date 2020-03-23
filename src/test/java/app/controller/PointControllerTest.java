package app.controller;

import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.repository.ValueRepository;
import app.service.PointService;
import app.service.impl.AttributeServiceImpl;
import app.service.impl.PointServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointController pointController;

//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));

    @Test
    void shouldReturnListOfPoints() {
    }

    @Test
    void getPointById() {
    }

    @Test
    void getPointAttributes() {
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