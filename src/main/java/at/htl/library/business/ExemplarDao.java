package at.htl.library.business;

import at.htl.library.model.Exemplar;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class ExemplarDao {

    @PersistenceContext
    public EntityManager em;

    public Exemplar get(long id) {
        TypedQuery<Exemplar> entities = em.createNamedQuery("Exemplar.findById",Exemplar.class);
        entities.setParameter("Id",id);
        return  entities.getSingleResult();
    }
}
