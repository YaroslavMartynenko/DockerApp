package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.domain.Value;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.repository.ValueRepository;
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final AttributeRepository attributeRepository;
    private final ValueRepository valueRepository;

    @Override
    public Point getPointById(Long id) {
        return pointRepository.findPointById(id);
    }

    @Override
    public Point getPointByCoordinates(BigDecimal longtitude, BigDecimal latitude) {

        return pointRepository.findPointByLongtitudeAndLatitude(longtitude, latitude);
    }

    @Override
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    @Override
    public void addNewPoint(Point point) {
        pointRepository.save(point);
    }

    @Override
    public void updatePoint(Point point) {
        pointRepository.save(point);
    }

    @Override
    public void deletePointById(Long id) {
        pointRepository.deletePointById(id);
    }

    @Override
    public void deletePointByCoordinates(BigDecimal longtitude, BigDecimal latitude) {
        pointRepository.deletePointByLongtitudeAndLatitude(longtitude, latitude);
    }

    @Override
    public void addAttributeToPoint(Long attributeId, Long pointId, String value) {
        Attribute attribute = attributeRepository.findAttributeById(attributeId);
        Point point = pointRepository.findPointById(pointId);
        Value newValue = new Value(null, point, attribute, value);
        valueRepository.save(newValue);
    }

    @Override
    public List<Attribute> getPointAttributes(Long pointId) {
        Point point = pointRepository.findPointById(pointId);
        Hibernate.initialize(point.getValues());
        List<Value> values = point.getValues();
        List<Attribute> attributes = new ArrayList<>();
        for (Value v : values) {
            attributes.add(v.getAttribute());
        }
        return attributes;
    }

}

