package app.service;

import app.domain.Attribute;
import app.domain.Point;

import java.math.BigDecimal;
import java.util.List;

public interface PointService {
    Point getPointById(Long id);

    Point getPointByCoordinates(BigDecimal longtitude, BigDecimal latitude);

    List<Point> getAllPoints();

    void addNewPoint(Point point);

    void updatePoint(Point point);

    void deletePointById(Long id);

    void deletePointByCoordinates(BigDecimal longtitude, BigDecimal latitude);

    void addAttributeToPoint(Long attributeId, Long pointId, String value);

    public List<Attribute> getPointAttributes(Long pointId);

}
