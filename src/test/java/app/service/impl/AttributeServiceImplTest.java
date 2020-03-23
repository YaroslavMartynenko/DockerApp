package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributeExistsException;
import app.exception.EmptyListException;
import app.exception.WrongIdException;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = AttributeServiceImpl.class)
class AttributeServiceImplTest {

    @MockBean
    private AttributeRepository attributeRepository;

    @MockBean
    private PointRepository pointRepository;

    @Autowired
    AttributeServiceImpl attributeService;

    private static List<Point> points;

    private static List<Attribute> attributes;

    private static final Attribute ATTRIBUTE = Attribute
            .builder()
            .id(1L)
            .name("Attribute 1")
            .build();

    @BeforeAll
    static void setUp() {
        points = new ArrayList<>();
        points.add(Point.builder().id(1L).longtitude(new BigDecimal(1.1))
                .latitude(new BigDecimal(1.1)).name("Point 1").build());
        points.add(Point.builder().id(2L).longtitude(new BigDecimal(2.2))
                .latitude(new BigDecimal(2.2)).name("Point 2").build());
        points.add(Point.builder().id(3L).longtitude(new BigDecimal(3.3))
                .latitude(new BigDecimal(3.3)).name("Point 3").build());

        attributes = new ArrayList<>();
        attributes.add(Attribute.builder().id(1L).name("Attribute 1").build());
        attributes.add(Attribute.builder().id(2L).name("Attribute 2").build());
        attributes.add(Attribute.builder().id(3L).name("Attribute 3").build());
    }

    @Test
    void shouldReturnAttributeById() {
        when(attributeRepository.findAttributeById(anyLong())).thenReturn(attributes.get(0));
        Attribute actual = attributeService.getAttributeById(1L);
        verify(attributeRepository, times(1)).findAttributeById(anyLong());
        assertEquals(ATTRIBUTE, actual);
    }

    @Test
    void shouldThrowWrongIdExceptionWhenAttributeWithSuchIdDoesNotExist() {
        when(attributeRepository.findAttributeById(anyLong())).thenReturn(null);
        assertThrows(WrongIdException.class, () -> {
            attributeService.getAttributeById(1L);
        });
    }

    @Test
    void shouldReturnListOfAttributes() {
        when(attributeRepository.findAll()).thenReturn(attributes);
        List<Attribute> actual = attributeService.getAllAttributes();
        List<Attribute> expected = new ArrayList<>();
        expected.add(Attribute.builder().id(1L).name("Attribute 1").build());
        expected.add(Attribute.builder().id(2L).name("Attribute 2").build());
        expected.add(Attribute.builder().id(3L).name("Attribute 3").build());
        verify(attributeRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEmptyListExceptionWhenAttributeListIsEmpty() {
        when(attributeRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EmptyListException.class, () -> {
            attributeService.getAllAttributes();
        });
    }

    @Test
    void shouldSaveNewAttribute() {
        doReturn(null).when(attributeRepository).findAttributeByName(anyString());
        attributeService.addNewAttribute(ATTRIBUTE);
        verify(attributeRepository, times(1)).findAttributeByName(anyString());
        verify(attributeRepository, times(1)).save(any(Attribute.class));
    }

    @Test
    void shouldThrowAttributeExistsExceptionWhenSuchAttributeAlreadyExists() {
        doReturn(ATTRIBUTE).when(attributeRepository).findAttributeByName(anyString());
        assertThrows(AttributeExistsException.class, () -> {
            attributeService.addNewAttribute(ATTRIBUTE);
        });
    }

    @Test
    void deleteAttributeByIdShouldDeleteAttributeById() {
        doReturn(ATTRIBUTE).when(attributeRepository).findAttributeById(anyLong());
        attributeService.deleteAttributeById(1L);
        verify(attributeRepository, times(1)).findAttributeById(anyLong());
        verify(attributeRepository, times(1)).deleteAttributeById(anyLong());
    }

    @Test
    void shouldThrowWrongIdExceptionIfAttributeWithSuchIdDoesNotExist() {
        doReturn(null).when(attributeRepository).findAttributeById(anyLong());
        assertThrows(WrongIdException.class, () -> {
            attributeService.deleteAttributeById(1L);
        });
    }

    @Test
    void shouldReturnListOfPointsWithSuchAttribute() {
        when(attributeRepository.findAttributeById(anyLong())).thenReturn(attributes.get(0));
        when(pointRepository.findByValues_Attribute(any(Attribute.class))).thenReturn(points);
        List<Point> actual = attributeService.getPointsWithAttribute(1L);
        List<Point> expected = new ArrayList<>();
        expected.add(Point.builder().id(1L).longtitude(new BigDecimal(1.1))
                .latitude(new BigDecimal(1.1)).name("Point 1").build());
        expected.add(Point.builder().id(2L).longtitude(new BigDecimal(2.2))
                .latitude(new BigDecimal(2.2)).name("Point 2").build());
        expected.add(Point.builder().id(3L).longtitude(new BigDecimal(3.3))
                .latitude(new BigDecimal(3.3)).name("Point 3").build());
        verify(attributeRepository, times(1)).findAttributeById(anyLong());
        verify(pointRepository, times(1)).findByValues_Attribute(any(Attribute.class));
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEmptyListExceptionWhenPointListIsEmpty() {
        when(attributeRepository.findAttributeById(anyLong())).thenReturn(attributes.get(0));
        when(pointRepository.findByValues_Attribute(any(Attribute.class))).thenReturn(Collections.emptyList());
        assertThrows(EmptyListException.class, () -> {
            attributeService.getPointsWithAttribute(1L);
        });
    }
}
