package pl.polsl.AskYourNeighbor.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.AskYourNeighbor.model.dao.CategoryDao;
import pl.polsl.AskYourNeighbor.service.CategoryService;

import java.util.List;

import static pl.polsl.AskYourNeighbor.constant.ResourceUrl.CATEGORIES;

@RestController
@RequestMapping(CATEGORIES)
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDao> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{idCategory}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDao getCategoryById(@PathVariable Long idCategory) {
        return categoryService.findCategoryById(idCategory);
    }
}
