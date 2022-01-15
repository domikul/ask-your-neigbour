package pl.polsl.AskYourNeighbor.service;

import org.springframework.stereotype.Service;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.dao.CategoryDao;
import pl.polsl.AskYourNeighbor.repository.CategoryRepository;
import pl.polsl.AskYourNeighbor.constant.ExceptionMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDao> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDao::new)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDao findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory).map(CategoryDao::new).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));
    }
}
