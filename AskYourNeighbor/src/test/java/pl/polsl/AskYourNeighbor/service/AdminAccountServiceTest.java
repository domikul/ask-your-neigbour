package pl.polsl.AskYourNeighbor.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.dao.AdminAccountDao;
import pl.polsl.AskYourNeighbor.repository.AdminAccountRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminAccountServiceTest {

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private AdminAccountService adminAccountService;

    @BeforeEach
    public void setup() {
        adminAccountRepository.deleteAll();
    }

    @Test
    void findAccountById_correctId() {
        AdminAccount adminAccount = createSampleAdminAccount();
        AdminAccount savedRecord = adminAccountRepository.save(adminAccount);

        AdminAccountDao result = adminAccountService.findAdminAccountById(savedRecord.getId());

        assertThat(result.getIdAdminAccount()).isEqualTo(savedRecord.getId());
        assertThat(result.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void findAccountById_incorrectId() {
        try {
            adminAccountService.findAdminAccountById(999L);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void getAllAccounts_noRecordsInDatabase() {
        List<AdminAccountDao> list = adminAccountService.getAllAccounts();
        assertThat(list).isEqualTo(Collections.emptyList());
    }

    @Test
    void getAllAccounts_recordsExistsInDatabase() {
        AdminAccount adminAccount = createSampleAdminAccount();
        adminAccountRepository.save(adminAccount);

        List<AdminAccountDao> list = adminAccountService.getAllAccounts();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void registerNewAccount_credentialsOk() {
        AdminAccountDao adminAccountDao = createSampleAdminAccountDao();

        adminAccountService.registerNewAccount(adminAccountDao);

        AdminAccount resultRecord = adminAccountRepository.findByUsername(adminAccountDao.getUsername());
        assertThat(resultRecord).isNotNull();
    }

    @Test
    void registerNewAccount_duplicateCredentials() {
        AdminAccountDao adminAccountDao = createSampleAdminAccountDao();

        try {
            adminAccountService.registerNewAccount(adminAccountDao);
            adminAccountService.registerNewAccount(adminAccountDao);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void deleteAccount_accountExist() {
        AdminAccount adminAccount = createSampleAdminAccount();
        AdminAccount savedRecord = adminAccountRepository.save(adminAccount);

        adminAccountService.deleteAccount(savedRecord.getId());

        Assertions.assertThat(adminAccountRepository.findByUsername(adminAccount.getUsername())).isNull();
    }

    @Test
    void deleteAccount_accountDoesntExist() {
        try {
            adminAccountService.deleteAccount(1L);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    private AdminAccount createSampleAdminAccount() {
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setUsername(USERNAME);
        adminAccount.setPassword(PASSWORD);
        return adminAccount;
    }

    private AdminAccountDao createSampleAdminAccountDao() {
        AdminAccountDao adminAccountDao = new AdminAccountDao();
        adminAccountDao.setUsername(USERNAME);
        adminAccountDao.setPassword(PASSWORD);
        return adminAccountDao;
    }

}
