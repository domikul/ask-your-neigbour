package pl.polsl.AskYourNeighbor.model.dao;

import pl.polsl.AskYourNeighbor.model.Category;

public class CategoryDao {

    private Long id;
    private String name;

    public CategoryDao() {
    }

    public CategoryDao(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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
