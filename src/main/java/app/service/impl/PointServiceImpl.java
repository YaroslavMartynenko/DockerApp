package app.service.impl;

import app.domain.Point;
import app.repository.PointRepository;
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

//    @Autowired
//    public PointServiceImpl(PointRepository pointRepository) {
//        this.pointRepository = pointRepository;
//    }

//    public Point getPointById(Long id) {
//        return pointRepository.getPointById(id);
//    }
//
//    public Point getPointByCoordinates(String coordinates) {
//        return pointRepository.getPointByCoordinates(coordinates);
//    }
//
//    public List<Point> getAllPoints() {
//        return pointRepository.getAll();
//    }
//
//    public void addNewPoint(Point point) {
//        pointRepository.save(point);
//    }
//
//    public void updatePoint(Point point) {
//        pointRepository.save(point);
//    }
//
//    public void deletePointById(Long id) {
//        pointRepository.deletePointById(id);
//    }
//
//    public void deletePointByCoordinates(String coordinates) {
//        pointRepository.deletePointByCoordinates(coordinates);
//    }
}
