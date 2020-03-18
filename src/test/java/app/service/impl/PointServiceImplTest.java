package app.service.impl;

import app.domain.Point;
import app.exception.EmptyListException;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.repository.ValueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PointServiceImplTest.class )
@ContextConfiguration(classes = PointServiceImpl.class)
class PointServiceImplTest {

    private List<Point> points = getPoints();



    @MockBean
    private PointRepository pointRepository;

    @MockBean
    private AttributeRepository attributeRepository;

    @MockBean
    private ValueRepository valueRepository;
    @Autowired
    private PointServiceImpl pointService;
    @Test
    void getPointById() {
    }

    @Test
    void shouldGetAllPoints() {
        when(pointRepository.findAll()).thenReturn(points);
        List<Point> actual = pointService.getAllPoints();
        List<Point> expected = new ArrayList<>();
        points.add(Point.builder().id(1L).build());
        points.add(Point.builder().id(2L).build());
        points.add(Point.builder().id(3L).build());
        verify(pointRepository, times(1)).findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenPointEmpty() {
        when(pointRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(EmptyListException.class, () -> {
            pointService.getAllPoints();
        });
    }


    @Test
    void addNewPoint() {
    }

    @Test
    void deletePointById() {
    }

    @Test
    void addAttributeToPoint() {
    }

    @Test
    void getPointAttributes() {
        getPointById();
    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        points.add(Point.builder().id(1L).build());
        points.add(Point.builder().id(2L).build());
        points.add(Point.builder().id(3L).build());
        return points;
    }
}