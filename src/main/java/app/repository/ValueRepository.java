package app.repository;

import app.domain.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository <Value, Long> {
    List<Value> findAll ();
}
