package at.htl.library.business;

import at.htl.library.model.*;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InitBean {

    @Inject
    EntityManager em;

    @Transactional
    void init(@Observes StartupEvent event){
        System.err.print("******** hello");
        CD cd =new CD("Eine kleine Nachtmusik",9.11,"classic","Mozart",123.1);
        CD cd2 = new CD("The Great War",15.00,"Power Metal","Sabaton",48.5);
        em.persist(cd);
        em.persist(cd2);
        Book book = new Book("How TO",18.00,"non-fiction/comedy/science","Randall Munroe",306);

        em.persist(book);
        Person p = new Person("Meier");
        Person p2 = new Person("Hofer");
        em.persist(p);
        em.persist(p2);
        em.flush();
        Exemplar e = new Exemplar(cd, Weariness.undamaged);
        Exemplar e2 = new Exemplar(cd,Weariness.used);
        Exemplar e3 = new Exemplar(cd2,Weariness.heavilyUsed);
        Exemplar e4 = new Exemplar(cd2,Weariness.undamaged);

        Exemplar e5 = new Exemplar(book,Weariness.used);
        Exemplar e6 = new Exemplar(book,Weariness.unusable);
        em.persist(e);
        em.persist(e2);
        em.persist(e3);
        em.persist(e4);
        em.persist(e5);
        em.persist(e6);

        List<Exemplar> exemplars = new ArrayList<>();
        exemplars.add(e);
        exemplars.add(e5);
        Loan l = new Loan(p,exemplars, LocalDate.now(),LocalDate.now());
        em.persist(l);


        exemplars = new ArrayList<>();
        exemplars.add(e3);
        exemplars.add(e4);
        Loan l2 = new Loan(p2,exemplars,LocalDate.now(),LocalDate.now().plusWeeks(2));
    }
}
