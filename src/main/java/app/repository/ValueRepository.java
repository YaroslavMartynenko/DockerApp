package app.repository;

import app.domain.Value;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@AllArgsConstructor(onConstructor = @__(@Autowired))
//@Transactional
@Repository
public class ValueRepository {

//    @PersistenceContext
//    private final EntityManager entityManager;
//
//
//    public void saveValue (Value value){
//        entityManager.persist(value);
//    }

}
