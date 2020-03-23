package app.service;

import app.domain.Attribute;
import app.domain.Point;

import java.util.List;

public interface PointService {
    Point getPointById(Long id);

    List<Point> getAllPoints();

    void addNewPoint(Point point);

    void deletePointById(Long id);

    void addAttributeToPoint(Long attributeId, Long pointId, String value);

    List<Attribute> getPointAttributes(Long pointId);
}
