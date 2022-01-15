package pl.polsl.AskYourNeighbor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.Category;
import pl.polsl.AskYourNeighbor.model.Form;
import pl.polsl.AskYourNeighbor.model.Unit;
import pl.polsl.AskYourNeighbor.model.dao.FormDao;
import pl.polsl.AskYourNeighbor.repository.AdminAccountRepository;
import pl.polsl.AskYourNeighbor.repository.CategoryRepository;
import pl.polsl.AskYourNeighbor.repository.FormRepository;
import pl.polsl.AskYourNeighbor.repository.UnitRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FormServiceTest {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormService formService;

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AdminAccount adminAccount;
    private Category category;
    private Unit unit;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        formRepository.deleteAll();

        this.adminAccount = insertAdminAccountToDatabase();
        this.category = insertCategoryToDatabase();
        this.unit = insertUnitToDatabase();

    }

    @Test
    void getAllActiveForms_noRecordsInDatabase() {
        List<FormDao> list = formService.getAllActiveForms();
        assertThat(list).isEqualTo(Collections.emptyList());
    }

    @Test
    void getAllActiveForms_recordExistsInDatabase() {
        Form form = createSampleForm();
        form.setAvailabilityStart(LocalDate.now());
        formRepository.save(form);

        List<FormDao> list = formService.getAllActiveForms();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getNameOfApplicant()).isEqualTo(NAME_OF_APPLICANT);
    }

    @Test
    void getFormsQueue_noRecordsInDatabase() {
        List<FormDao> list = formService.getFormsQueue();
        assertThat(list).isEqualTo(Collections.emptyList());
    }

    @Test
    void getFormsQueue_recordExistsInDatabase() {
        Form form = createSampleForm();
        formRepository.save(form);

        List<FormDao> list = formService.getFormsQueue();

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getNameOfApplicant()).isEqualTo(NAME_OF_APPLICANT);
    }

    @Test
    void addNewForm() {
        FormDao formDao = createSampleFormDao();

        formService.addNewForm(formDao);

        List<FormDao> list = formService.getFormsQueue();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getNameOfApplicant()).isEqualTo(NAME_OF_APPLICANT);
    }

    @Test
    void addNewForm_wrongIdCategory() {
        FormDao formDao = createSampleFormDao();
        formDao.setIdCategory(999L);

        try {
            formService.addNewForm(formDao);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void addNewForm_wrongIdUnit() {
        FormDao formDao = createSampleFormDao();
        formDao.setIdUnit(999L);

        try {
            formService.addNewForm(formDao);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void findFormById_correctId() {
        Form form = createSampleForm();
        Form savedRecord = formRepository.save(form);

        FormDao result = formService.findFormById(savedRecord.getId());

        assertThat(result.getId()).isEqualTo(savedRecord.getId());
        assertThat(result.getNameOfApplicant()).isEqualTo(NAME_OF_APPLICANT);
    }

    @Test
    void findFormById_incorrectId() {
        try {
            formService.findFormById(999L);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    @DirtiesContext
    void deleteFormById_correctId() {
        formRepository.deleteAll();
        Form form = createSampleForm();
        Form savedRecord = formRepository.save(form);

        formService.deleteFormById(savedRecord.getId());

        try {
            formService.findFormById(savedRecord.getId());
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void deleteFormById_incorrectId() {
        try {
            formService.deleteFormById(999L);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    void acceptForm() {
        Form form = createSampleForm();
        form.setAcceptingPerson(null);
        form.setAvailabilityStart(null);
        Form savedRecord = formRepository.save(form);

        formService.acceptForm(savedRecord.getId(), adminAccount);

        FormDao acceptedForm = formService.findFormById(savedRecord.getId());
        assertThat(acceptedForm.getIdAcceptingPerson()).isEqualTo(adminAccount.getId());
    }

    @Test
    void acceptForm_wrongId() {
        try {
            formService.acceptForm(999L, adminAccount);
            fail("Method should throw an exception");
        } catch (IncorrectRequestException e) {
            assertTrue(true);
        }
    }

    private final String NAME_OF_APPLICANT = "sampleName";
    private final String CITY = "city";
    private final String STREET = "street";
    private final String NUMBER = "1";
    private final Long PHONE_NUMBER = 123456789L;
    private final String PRODUCT_NAME = "productName";
    private final String DESCRIPTION = "description";
    private final Integer AMOUNT = 100;
    private final BigDecimal PRICE = new BigDecimal("39.99");
    private final LocalDate NOW = LocalDate.now();


    private Form createSampleForm() {
        Form form = new Form();
        form.setNameOfApplicant(NAME_OF_APPLICANT);
        form.setCity(CITY);
        form.setStreet(STREET);
        form.setNumber(NUMBER);
        form.setPhoneNumber(PHONE_NUMBER);
        form.setProductName(PRODUCT_NAME);
        form.setDescription(DESCRIPTION);
        form.setCategory(category);
        form.setAmount(AMOUNT);
        form.setUnit(unit);
        form.setPrice(PRICE);
        form.setSendDate(NOW);
        return form;
    }

    private FormDao createSampleFormDao() {
        FormDao form = new FormDao();
        form.setNameOfApplicant(NAME_OF_APPLICANT);
        form.setCity(CITY);
        form.setStreet(STREET);
        form.setNumber(NUMBER);
        form.setPhoneNumber(PHONE_NUMBER);
        form.setProductName(PRODUCT_NAME);
        form.setDescription(DESCRIPTION);
        form.setIdCategory(category.getId());
        form.setAmount(AMOUNT);
        form.setIdUnit(unit.getId());
        form.setPrice(PRICE);
        form.setSendDate(NOW);
        return form;
    }

    private AdminAccount insertAdminAccountToDatabase() {
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setId(1L);
        return adminAccountRepository.save(adminAccount);
    }

    private Category insertCategoryToDatabase() {
        Category category = new Category();
        category.setId(1L);
        return categoryRepository.save(category);
    }

    private Unit insertUnitToDatabase() {
        Unit unit = new Unit();
        unit.setId(1L);
        return unitRepository.save(unit);
    }

}
