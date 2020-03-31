package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributeExistsException;
import app.exception.EmptyListException;
import app.exception.WrongIdException;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.util.InitTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private List<Point> points = InitTestData.getPoints();

    private List<Attribute> attributes = InitTestData.getAattributes();

    private static final Attribute ATTRIBUTE = Attribute
            .builder()
            .id(1L)
            .name("Attribute 1")
            .build();

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
        when(attributeRepository.findAttributeByName(anyString())).thenReturn(null);
        when(attributeRepository.save(any(Attribute.class))).thenReturn(ATTRIBUTE);
        Attribute actual = attributeService.addNewAttribute(ATTRIBUTE);
        verify(attributeRepository, times(1)).findAttributeByName(anyString());
        verify(attributeRepository, times(1)).save(any(Attribute.class));
        assertEquals(ATTRIBUTE, actual);
    }

    @Test
    void shouldThrowAttributeExistsExceptionWhenSuchAttributeAlreadyExists() {
        when(attributeRepository.findAttributeByName(anyString())).thenReturn(attributes.get(0));
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
        expected.add(Point.builder()
                .id(1L)
                .longtitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
                .name("Point 1")
                .build());
        expected.add(Point.builder()
                .id(2L)
                .longtitude(new BigDecimal(2.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(2.0).setScale(1, RoundingMode.DOWN))
                .name("Point 2")
                .build());
        expected.add(Point.builder()
                .id(3L)
                .longtitude(new BigDecimal(3.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(3.0).setScale(1, RoundingMode.DOWN))
                .name("Point 3")
                .build());
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
