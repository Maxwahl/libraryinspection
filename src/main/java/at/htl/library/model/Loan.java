package at.htl.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Loan.findById",query = "select l from Loan l where l.Id= :Id"),
        @NamedQuery(name = "Loan.findAll",query = "select l from Loan l"),
        @NamedQuery(name="Loan.unfinishedByPerson",query = "select l from Loan l where l.person.Id=:Id and l.doAR IS null"),
        @NamedQuery(name="Loan.unfinished",query = "select l from Loan l where l.doAR IS null")
})
@XmlRootElement
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE})
    Person person;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "loan_exemplar",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "exemplar_id")
    )
    @JsonIgnore
    List<Exemplar> exemplars;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate doT;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate doAR;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate doR;

    public Loan(Person person, List<Exemplar> exemplars, LocalDate doT, LocalDate doR) {
        this.person = person;
        this.exemplars = exemplars;
        this.doT = doT;
        this.doR = doR;
    }

    public Loan() {
    }

    //region getter and setter
    public Long getId() {
        return Id;
    }

    private void setId(Long id) {
        Id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getDoT() {
        return doT;
    }

    public void setDoT(LocalDate doT) {
        this.doT = doT;
    }

    public LocalDate getDoR() {
        return doR;
    }

    public void setDoR(LocalDate doR) {
        this.doR = doR;
    }

    public List<Exemplar> getExemplars() {
        return exemplars;
    }

    public void setExemplar(List<Exemplar> exemplars) {
        this.exemplars = exemplars;
    }

    public LocalDate getDoAR() {
        return doAR;
    }

    public void setDoAR(LocalDate doAR) {
        this.doAR = doAR;
    }

    public  void addExemplar(Exemplar exemplar){
        this.exemplars.add(exemplar);
        exemplar.addLoan(this);
    }
    //endregion


    @Override
    public String toString() {
        return "Loan{" +
                "Id=" + Id +
                ", person=" + person +
                ", doT=" + doT +
                ", doAR=" + doAR +
                ", doR=" + doR +
                '}';
    }
}
