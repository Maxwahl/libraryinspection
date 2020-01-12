package at.htl.library.business;

import at.htl.library.model.Exemplar;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ExemplarDaoPan implements PanacheRepository<Exemplar> {
    @Transactional
    public Exemplar save(Exemplar ex) {
            this.persist(ex);
            return findById(ex.getId());
    }
}
