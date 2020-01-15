package at.htl.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
    List<Loan> loans;
    public Person(String name) {
        this.name = name;
    }

    public Person() {
        loans = new ArrayList<>();
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }


    @JsonIgnore
    public List<Loan> getLoans() {
        return loans;
    }

    public Person setId(Long id) {
        Id = id;
        return this;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setLoans(List<Loan> loans) {
        this.loans = loans;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", loans=" + loans +
                '}';
    }
}
