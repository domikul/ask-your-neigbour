package pl.polsl.AskYourNeighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.AskYourNeighbor.model.Form;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    List<Form> findAllByAvailabilityStartIsNotNull();

    List<Form> findAllByAvailabilityStartIsNull();

    void deleteAllByAvailabilityEndBefore(LocalDate now);

}
