package at.htl.library.business;

import at.htl.library.model.Exemplar;
import at.htl.library.model.Loan;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.commons.collections4.CollectionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped

public class LoanDaoPan implements PanacheRepository<Loan> {
    @Inject
    ExemplarDaoPan exemplarDao;

    public List<Loan> getUnfinishedByPerson(long pid) {

        List<Loan> unfinishedLoans = this.list("person.id",pid);
        unfinishedLoans = unfinishedLoans.stream().filter(it->it.getDoAR() == null).collect(Collectors.toList());
        return unfinishedLoans;
    }

    public Loan getUnfinishedLoanWithExemplars(List<Exemplar> exemplars) {
        List<Exemplar> shadow = new ArrayList<>();
        for (Exemplar e: exemplars){
            shadow.addAll(exemplarDao.list("id",e.getId()));
        }
        Loan loan = null;
        List<Loan> unfinishedLoans = this.listAll();
        unfinishedLoans = unfinishedLoans.stream().filter(it->it.getDoAR() == null).collect(Collectors.toList());
        for (Loan l:unfinishedLoans) {
            if(CollectionUtils.isEqualCollection(l.getExemplars(),shadow)) {
                loan = l;
            }
        }
        return loan;
    }
    @Transactional
    public Loan finish(Loan l) {
            l = findById(l.getId());
            l.setDoAR(LocalDate.now());
            persist(l);
            return findById(l.getId());
    }
}
