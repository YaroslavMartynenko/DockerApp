package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.domain.Value;
import app.exception.AttributePresentsException;
import app.exception.EmptyListException;
import app.exception.PointExistsException;
import app.exception.WrongIdException;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.repository.ValueRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = PointServiceImpl.class)
class PointServiceImplTest {

    @MockBean
    private PointRepository pointRepository;

    @MockBean
    private AttributeRepository attributeRepository;

    @MockBean
    private ValueRepository valueRepository;

    @Autowired
    private PointServiceImpl pointService;

    private static List<Point> points;

    private static List<Attribute> attributes;

    private static final Point POINT = Point
            .builder()
            .id(1L)
            .longtitude(new BigDecimal(1.1))
            .latitude(new BigDecimal(1.1))
            .name("Point 1")
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
    void shouldReturnPointById() {
        when(pointRepository.findPointById(anyLong())).thenReturn(points.get(0));
        Point actual = pointService.getPointById(1L);
        verify(pointRepository, times(1)).findPointById(anyLong());
        assertEquals(POINT, actual);
    }

    @Test
    void shouldThrowWrongIdExceptionWhenPointWithSuchIdDoesNotExist() {
        when(pointRepository.findPointById(anyLong())).thenReturn(null);
        assertThrows(WrongIdException.class, () -> {
            pointService.getPointById(1L);
        });
    }

    @Test
    void shouldReturnListOfPoints() {
        when(pointRepository.findAll()).thenReturn(points);
        List<Point> actual = pointService.getAllPoints();
        List<Point> expected = new ArrayList<>();
        expected.add(Point.builder().id(1L).longtitude(new BigDecimal(1.1))
                .latitude(new BigDecimal(1.1)).name("Point 1").build());
        expected.add(Point.builder().id(2L).longtitude(new BigDecimal(2.2))
                .latitude(new BigDecimal(2.2)).name("Point 2").build());
        expected.add(Point.builder().id(3L).longtitude(new BigDecimal(3.3))
                .latitude(new BigDecimal(3.3)).name("Point 3").build());
        verify(pointRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEmptyListExceptionWhenListOfPointsIsEmpty() {
        when(pointRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(EmptyListException.class, () -> {
            pointService.getAllPoints();
        });
    }

    @Test
    void shouldSaveNewPoint() {
        doReturn(null)
                .when(pointRepository)
                .findPointByLongtitudeAndLatitude(any(BigDecimal.class), any(BigDecimal.class));
        pointService.addNewPoint(POINT);
        verify(pointRepository, times(1))
                .findPointByLongtitudeAndLatitude(any(BigDecimal.class), any(BigDecimal.class));
        verify(pointRepository, times(1)).save(any(Point.class));

    }

    @Test
    void shouldThrowPointExistsExceptionWhenSuchPointAlreadyExists() {
        doReturn(points.get(0))
                .when(pointRepository)
                .findPointByLongtitudeAndLatitude(any(BigDecimal.class), any(BigDecimal.class));
        assertThrows(PointExistsException.class, () -> {
            pointService.addNewPoint(POINT);
        });
    }

    @Test
    void shouldDeletePointById() {
        doReturn(points.get(0)).when(pointRepository).findPointById(anyLong());
        pointService.deletePointById(1L);
        verify(pointRepository, times(1)).findPointById(anyLong());
        verify(pointRepository, times(1)).deletePointById(anyLong());
    }

    @Test
    void shouldThrowWrongIdExceptionWhenPointWithSuchIdDoesntExist() {
        doReturn(null)
                .when(pointRepository)
                .findPointById(anyLong());
        assertThrows(WrongIdException.class, () -> {
            pointService.deletePointById(1L);
        });
    }

    @Test
    void shouldAddAttributeToPoint() {
        doReturn(points.get(0)).when(pointRepository).findPointById(anyLong());
        doReturn(attributes).when(attributeRepository).findByValues_Point(any(Point.class));
        doReturn(attributes.get(0)).when(attributeRepository).findAttributeById(anyLong());
        pointService.addAttributeToPoint(4L, 1L, "Some value");
        verify(pointRepository, times(1)).findPointById(anyLong());
        verify(attributeRepository, times(1)).findByValues_Point(any(Point.class));
        verify(attributeRepository, times(1)).findAttributeById(anyLong());
        verify(valueRepository, times(1)).save(any(Value.class));
    }

    @Test
    void shouldThrowEmptyListExceptionWhenAttributeListIsEmpty() {
        doReturn(points.get(0)).when(pointRepository).findPointById(anyLong());
        doReturn(Collections.emptyList()).when(attributeRepository).findByValues_Point(any(Point.class));
        assertThrows(EmptyListException.class, () -> {
            pointService.addAttributeToPoint(1L, 1L, "Some value");
        });
    }

    @Test
    void shouldThrowAttributePresentExceptionWhenPointContainsAttribute() {
        doReturn(points.get(0)).when(pointRepository).findPointById(anyLong());
        doReturn(attributes).when(attributeRepository).findByValues_Point(any(Point.class));
        assertThrows(AttributePresentsException.class, () -> {
            pointService.addAttributeToPoint(1L, 1L, "Some value");
        });
    }

    @Test
    void shouldThrowWrongIdExceptionWhenAttributeWithSuchIdDoesNotExist() {
        doReturn(points.get(0)).when(pointRepository).findPointById(anyLong());
        doReturn(attributes).when(attributeRepository).findByValues_Point(any(Point.class));
        doReturn(null).when(attributeRepository).findAttributeById(anyLong());
        assertThrows(WrongIdException.class, () -> {
            pointService.addAttributeToPoint(4L, 1L, "Some string");
        });
    }

    @Test
    void shouldReturnListOfAttributesThatContainsThisPoint() {
        when(pointRepository.findPointById(anyLong())).thenReturn(points.get(0));
        when(attributeRepository.findByValues_Point(any(Point.class))).thenReturn(attributes);
        List<Attribute> actual = pointService.getPointAttributes(1L);
        List<Attribute> expected = new ArrayList<>();
        expected.add(Attribute.builder().id(1L).name("Attribute 1").build());
        expected.add(Attribute.builder().id(2L).name("Attribute 2").build());
        expected.add(Attribute.builder().id(3L).name("Attribute 3").build());
        verify(pointRepository, times(1)).findPointById(anyLong());
        verify(attributeRepository, times(1)).findByValues_Point(any(Point.class));
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowEmptyListExceptionWhenListOfAttributesIsEmpty() {
        when(pointRepository.findPointById(anyLong())).thenReturn(points.get(0));
        when(attributeRepository.findByValues_Point(any(Point.class))).thenReturn(Collections.emptyList());
        assertThrows(EmptyListException.class, () -> {
            pointService.getPointAttributes(1L);
        });
    }
}
