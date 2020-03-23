package app.repository;

import app.domain.Attribute;
import app.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Attribute findAttributeById(Long id);

    Attribute findAttributeByName(String name);

    List<Attribute> findAll();

    void deleteAttributeById(Long id);

    List<Attribute> findByValues_Point(Point point);
}

