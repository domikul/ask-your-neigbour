package pl.polsl.AskYourNeighbor.model.dao;

import pl.polsl.AskYourNeighbor.model.Unit;

public class UnitDao {

    private Long id;
    private String name;

    public UnitDao() {
    }

    public UnitDao(Unit unit) {
        this.id = unit.getId();
        this.name = unit.getName();
    }

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
}
