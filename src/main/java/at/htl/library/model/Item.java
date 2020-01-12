package at.htl.library.model;




import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.json.JsonValue;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "Item.findById",query = "select i from Item i where i.Id= :Id"),
        @NamedQuery(name = "Item.findAll",query = "select i from Item i"),
        @NamedQuery(name="Item.getStats",query = "select i,count(i.id) from Loan l join l.exemplars e join e.item i group by i.id order by count(i.id) desc")
        //select e.item_id,count(*) from loan_exemplar le join exemplar e on le.exemplars_id = e.id group by e.item_id
})
@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS ,include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({

        @JsonSubTypes.Type(value = CD.class, name = "cd"),
        @JsonSubTypes.Type(value = Book.class, name = "book")})
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String name;
    String genre;
    double price;
    @OneToMany(mappedBy = "item",fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE})
    List<Exemplar> exemplars;

    //region constructors
    public Item(String name,String genre, double price){
        this.name = name;
        this.genre=genre;
        this.price = price;
        this.exemplars=new ArrayList<>();

    }

    public Item() {
        this.exemplars=new ArrayList<>();
    }
    //endregion

    //region getter and setter
    public Long getId() {
        return Id;
    }

    private void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void addExemplar(Exemplar exemplar){
        this.exemplars.add(exemplar);
    }

    public abstract ObjectNode jsonify(ObjectNode objectNode);
//endregion
}
