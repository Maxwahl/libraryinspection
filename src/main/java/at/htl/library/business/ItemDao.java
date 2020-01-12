package at.htl.library.business;

import at.htl.library.model.Book;
import at.htl.library.model.CD;
import at.htl.library.model.Exemplar;
import at.htl.library.model.Item;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

@ApplicationScoped
public class ItemDao {

    @PersistenceContext
    public EntityManager em;


    public List<Item> get() {
        TypedQuery<Item> entities = em.createNamedQuery("Item.findAll",Item.class);
        return  entities.getResultList();
    }

    public List<Object[]> getStatistics() {
        TypedQuery<Object[]> entities = em.createNamedQuery("Item.getStats",Object[].class);
        return  entities.getResultList();
    }

    public CD getCD(long id) {
        TypedQuery<CD> entities = em.createNamedQuery("CD.findById",CD.class);
        entities.setParameter("Id",id);
        return  entities.getSingleResult();
    }

    public Book getBook(long id) {
        TypedQuery<Book> entities = em.createNamedQuery("Book.findById",Book.class);
        entities.setParameter("Id",id);
        return  entities.getSingleResult();
    }

    @Transactional
    public Book saveBook(Book entity) {
       em.persist(entity);
        return entity;
    }
    @Transactional
    public CD saveCD(CD entity) {
            em.persist(entity);
            return entity;
    }
    @Transactional
    public Exemplar saveExemplar(Item entity) {
        Exemplar ex= new Exemplar(entity);
        em.persist(ex);
        return ex;
    }
    @Transactional
    public Item save(Item entity) {
        em.persist(entity);
        return entity;
    }
}
