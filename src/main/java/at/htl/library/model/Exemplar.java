package at.htl.library.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
@NamedQuery(name = "Exemplar.findById",query = "select e from Exemplar e where e.Id= :Id")
public class Exemplar {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name = "item_id")
    Item item;
    @Enumerated(EnumType.STRING)
    Weariness weariness;
    @ManyToMany(mappedBy = "exemplars")
            @JsonbTransient
    List<Loan> loans;

    //region constructors
    public Exemplar(Item item, Weariness weariness) {
        this.setItem(item);
        this.weariness = weariness;
        loans = new ArrayList<>();
        item.addExemplar(this);
    }

    public Exemplar(Item item) {
        this.item = item;
        this.weariness = Weariness.undamaged;
        loans = new ArrayList<>();
        item.addExemplar(this);
    }

    public Exemplar() {
    }
    //endregion

    //region getter and setter
    public Long getId() {
        return Id;
    }

    private void setId(Long id) {
        Id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Weariness getWeariness() {
        return weariness;
    }

    public void setWeariness(Weariness weariness) {
        this.weariness = weariness;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }
    //endregion


    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Exemplar{" +
                "Id=" + Id +
                ", item=" + item +
                ", weariness=" + weariness +
                ", loans=" + loans +
                '}';
    }
}
