package app.repository;

import app.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Attribute getAttributeById(Long id);

    Attribute getAttributeByName(String name);

    List<Attribute> getAll();

    void deleteAttributeById(Long id);

    void deleteAttributeByName(String name);

}
