package pl.polsl.AskYourNeighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.AskYourNeighbor.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
