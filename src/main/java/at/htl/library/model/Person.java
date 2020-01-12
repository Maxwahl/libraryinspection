package at.htl.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.findById",query = "select p from Person p where p.Id= :Id"),
        @NamedQuery(name = "Person.findAll",query = "select p from Person p")
})
public class    Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String name;
    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE})
    List<Loan> loans;
    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
