package at.htl.library.business;

import at.htl.library.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PersonDao {

    @PersistenceContext
    EntityManager em;


    public List<Person> get() {
        TypedQuery<Person> entities = em.createNamedQuery("Person.findAll",Person.class);
        return  entities.getResultList();
    }

    public Person get(long id) {
        TypedQuery<Person> entities = em.createNamedQuery("Person.findById",Person.class);
        entities.setParameter("Id",id);
        return  entities.getSingleResult();
    }
    @Transactional

    public void remove(Person entity) {
        entity= em.merge(entity);
        em.remove(entity);
    }
    @Transactional
    public Person save(Person entity) {
        em.persist(entity);
        return entity;
    }
    @Transactional

    public Person update(Person entity) {
        entity = em.merge(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }
}
