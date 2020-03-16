package app.repository;

import app.domain.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ValueRepository extends JpaRepository<Value, Long> {
    List<Value> findAll();
}
