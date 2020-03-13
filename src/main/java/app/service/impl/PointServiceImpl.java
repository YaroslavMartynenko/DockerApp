package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.repository.PointRepository;
import app.repository.ValueRepository;
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
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
    public void addAttributeToPoint(Attribute attribute, Point point, String value) {

    }

}
