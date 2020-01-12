package at.htl.library.business;


import at.htl.library.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemDaoPan implements PanacheRepository<Item> {
    @Inject
    ExemplarDaoPan exemplarDao;
    @Inject LoanDaoPan loanDao;

    public List<Object[]> getStatistics() {
        List<Object[]> result = new ArrayList<>();
        List<Item> items = this.listAll();
        List<Loan> loans = loanDao.listAll();
        for (Item i: items) {
            Long count = loans.stream().flatMap(it->it.getExemplars().stream()).filter(it->it.getItem().getId()==i.getId()).count();
            Object[] item = new Object[2];
            item[0] = i;
            item[1] = count.intValue();
            result.add(item);
        }
        result = result.stream().sorted((a,b)-> -(((Integer)a[1]).compareTo((Integer)(b[1])))).collect(Collectors.toList());
        return result;
    }
    @Transactional
    public Item save(Item entity) {
        this.persistAndFlush(entity);
        return findById(entity.getId());
    }

    public Book getBook(Long id) {
        return (Book) this.findById(id);
    }

    public CD getCD(Long id) {
        return (CD) this.findById(id);
    }

    public Exemplar saveExemplar(Item entity) {
        Exemplar ex= new Exemplar(entity);
        ex = exemplarDao.save(ex);
        return ex;
    }
}
