package at.htl.library.business;

import at.htl.library.model.Book;
import at.htl.library.model.Exemplar;
import at.htl.library.model.Loan;
import at.htl.library.model.Person;
import org.apache.commons.collections4.CollectionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LoanDao {

    @PersistenceContext
    EntityManager em;

    @Inject
    ExemplarDao exemplarDao;

    public List<Loan> getUnfinishedByPerson(long pid){
        TypedQuery<Loan> entities = em.createNamedQuery("Loan.unfinishedByPerson",Loan.class);
        entities.setParameter("Id",pid);
        return  entities.getResultList();
    }

    public Loan getUnfinishedLoanWithExemplars(List<Exemplar> exemplars) {
        List<Exemplar> shadowCopy= new ArrayList<>();
        exemplars.forEach(it->shadowCopy.add(exemplarDao.get(it.getId())));
        Loan loan =null;
        TypedQuery<Loan> entities = em.createNamedQuery("Loan.unfinished",Loan.class);
        for (Loan l: entities.getResultList()){
            if(CollectionUtils.isEqualCollection(l.getExemplars(),shadowCopy)) {
                loan = l;
            }
        }
        return loan;
    }

    @Transactional
    public Loan finish(Loan l) {
        l.setDoAR(LocalDate.now());
        return em.merge(l);
    }
}
