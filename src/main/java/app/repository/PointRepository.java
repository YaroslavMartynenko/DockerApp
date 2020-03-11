package app.repository;

import app.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
//    Point getPointById(Long id);
//
//    Point getPointByCoordinates(String coordinates);
//
//    List<Point> getAll();
//
//    void deletePointById(Long id);
//
//    void deletePointByCoordinates(String coordinates);

}
