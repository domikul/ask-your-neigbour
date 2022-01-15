package pl.polsl.AskYourNeighbor.service;

import pl.polsl.AskYourNeighbor.model.dao.CategoryDao;

import java.util.List;

public interface CategoryService {
    List<CategoryDao> getAllCategories();

    CategoryDao findCategoryById(Long idCategory);
}
