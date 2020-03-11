package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.repository.PointRepository;
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

    public Point getPointById(Long id) {
        return pointRepository.findPointById(id);
    }

    public Point getPointByCoordinates(BigDecimal longtitude, BigDecimal latitude) {
        return pointRepository.findPointByLongtitudeAndLatitude(longtitude, latitude);
    }

    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    public void addNewPoint(Point point) {
        pointRepository.save(point);
    }

    public void updatePoint(Point point) {
        pointRepository.save(point);
    }

    public void deletePointById(Long id) {
        pointRepository.deletePointById(id);
    }

    public void deletePointByCoordinates(BigDecimal longtitude, BigDecimal latitude) {
        pointRepository.deletePointByLongtitudeAndLatitude(longtitude, latitude);
    }

    public void addAttributeToPoint (Attribute attribute, Point point){

    }

}
