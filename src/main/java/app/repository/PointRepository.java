package app.repository;

import app.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findPointById(Long id);

    Point findPointByLongtitudeAndLatitude(BigDecimal longtitude, BigDecimal latitude);

    List<Point> findAll();

    void deletePointById(Long id);

    void deletePointByLongtitudeAndLatitude(BigDecimal longtitude, BigDecimal latitude);

}
