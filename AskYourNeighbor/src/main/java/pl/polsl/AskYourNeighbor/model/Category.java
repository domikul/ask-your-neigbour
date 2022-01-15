package pl.polsl.AskYourNeighbor.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category", targetEntity = Form.class)
    private List<Form> listOfForms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Form> getListOfForms() {
        return listOfForms;
    }

    public void setListOfForms(List<Form> listOfForms) {
        this.listOfForms = listOfForms;
    }
}
