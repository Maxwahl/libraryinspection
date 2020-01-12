package at.htl.library.model;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Book.findById",query = "select i from Book i where i.Id= :Id"),
})
@JsonTypeName("book")
public class Book extends Item{
    String author;
    int pages;

    //region constructors
    public Book(String name, double price,String genre, String author, int pages) {
        super(name,genre ,price);
        this.author = author;
        this.pages = pages;
    }

    public Book() {
    }

    @Override
    public ObjectNode jsonify(ObjectNode objectNode) {
        objectNode.put("id",this.getId());
        objectNode.put("name",this.getName());
        objectNode.put("genre",this.getGenre());
        objectNode.put("price",this.getPrice());
        objectNode.put("author",this.getAuthor());
        objectNode.put("pages",this.getPages());
        return objectNode;
    }
    //endregion

    //region getter and setter
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    //endregion
}
