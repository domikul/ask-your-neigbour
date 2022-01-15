package pl.polsl.AskYourNeighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.AskYourNeighbor.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
