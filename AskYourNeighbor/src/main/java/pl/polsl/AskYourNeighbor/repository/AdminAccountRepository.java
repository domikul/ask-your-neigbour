package pl.polsl.AskYourNeighbor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.AskYourNeighbor.model.AdminAccount;

import java.util.Optional;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

    AdminAccount findByUsername(String username);

    Optional<AdminAccount> findById(Long id);

}
