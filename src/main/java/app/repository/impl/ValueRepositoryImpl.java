package app.repository.impl;

import app.domain.Value;
import app.repository.ValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Repository
public class ValueRepositoryImpl implements ValueRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void saveValue(Value value) {
        entityManager.persist(value);
    }

}
