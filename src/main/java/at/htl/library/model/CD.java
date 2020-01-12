package at.htl.library.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CD.findById",query = "select i from CD i where i.Id= :Id"),
})
@JsonTypeName("cd")
public class CD extends Item {
    String composer;
    double runtime;

    //region constructors
    public CD(String name, double price,String genre ,String composer, double runtime) {
        super(name,genre ,price);
        this.composer = composer;
        this.runtime = runtime;
    }

    public CD() {
    }

    @Override
    public ObjectNode jsonify(ObjectNode objectNode) {
        objectNode.put("id",this.getId());
        objectNode.put("name",this.getName());
        objectNode.put("genre",this.getGenre());
        objectNode.put("price",this.getPrice());
        objectNode.put("composer",this.getComposer());
        objectNode.put("runtime",this.getRuntime());
        return objectNode;
    }
    //endregion

    //region getter and setter
    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }
    //endregion
}
