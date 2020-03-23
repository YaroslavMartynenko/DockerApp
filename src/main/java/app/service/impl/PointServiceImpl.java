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
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final AttributeRepository attributeRepository;
    private final ValueRepository valueRepository;

    @Override
    public Point getPointById(Long id) {
        Point point = pointRepository.findPointById(id);
        if (Objects.isNull(point)) {
            throw new WrongIdException();
        }
        return point;
    }

    @Override
    public List<Point> getAllPoints() {
        List<Point> points = pointRepository.findAll();
        if (points.isEmpty()) {
            throw new EmptyListException();
        }
        return points;
    }

    @Override
    public void addNewPoint(Point point) {
        Point pointFromDb = pointRepository
                .findPointByLongtitudeAndLatitude(point.getLongtitude(), point.getLatitude());
        if (Objects.nonNull(pointFromDb)) {
            throw new PointExistsException();
        }
        pointRepository.save(point);
    }

    @Override
    public void deletePointById(Long id) {
        Point point = pointRepository.findPointById(id);
        if (Objects.isNull(point)) {
            throw new WrongIdException();
        }
        pointRepository.deletePointById(id);
    }

    @Override
    public void addAttributeToPoint(Long attributeId, Long pointId, String value) {
        Point point = getPointById(pointId);
        List<Attribute> attributes = attributeRepository.findByValues_Point(point);
        if (!attributes.isEmpty()) {
            for (Attribute attribute : attributes) {
                if (attribute.getId().equals(attributeId)) {
                    throw new AttributePresentsException();
                }
            }
        }
        Attribute attribute = attributeRepository.findAttributeById(attributeId);
        if (Objects.isNull(attribute)) {
            throw new WrongIdException();
        }
        Value newValue = new Value(null, point, attribute, value);
        valueRepository.save(newValue);
    }

    @Override
    public List<Attribute> getPointAttributes(Long id) {
        Point point = getPointById(id);
        List<Attribute> attributes = attributeRepository.findByValues_Point(point);
        if (attributes.isEmpty()) {
            throw new EmptyListException();
        }
        return attributes;
    }
}

