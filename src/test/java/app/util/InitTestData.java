package app.util;

import app.domain.Attribute;
import app.domain.Point;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class InitTestData {

    public static List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        points.add(Point.builder()
                .id(1L)
                .longtitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(1.0).setScale(1, RoundingMode.DOWN))
                .name("Point 1")
                .build());
        points.add(Point.builder()
                .id(2L)
                .longtitude(new BigDecimal(2.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(2.0).setScale(1, RoundingMode.DOWN))
                .name("Point 2")
                .build());
        points.add(Point.builder()
                .id(3L)
                .longtitude(new BigDecimal(3.0).setScale(1, RoundingMode.DOWN))
                .latitude(new BigDecimal(3.0).setScale(1, RoundingMode.DOWN))
                .name("Point 3")
                .build());
        return points;
    }

    public static List<Attribute> getAattributes() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(Attribute.builder().id(1L).name("Attribute 1").build());
        attributes.add(Attribute.builder().id(2L).name("Attribute 2").build());
        attributes.add(Attribute.builder().id(3L).name("Attribute 3").build());
        return attributes;
    }
}
