package app.repository;

import app.domain.Attribute;
import app.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findPointById(Long id);

    Point findPointByLongtitudeAndLatitude(BigDecimal longtitude, BigDecimal latitude);

    List<Point> findAll();

    void deletePointById(Long id);

    List<Point> findByValues_Attribute (Attribute attribute);
}
