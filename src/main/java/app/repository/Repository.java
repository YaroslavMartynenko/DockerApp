package app.repository;

import app.domain.Human;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repository extends JpaRepository<Human, Long> {
    Human findHumanById(Long id);
    List<Human> findAll ();
}
