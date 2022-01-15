package pl.polsl.AskYourNeighbor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.polsl.AskYourNeighbor.constant.ResourceUrl.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FormControllerTest {

    @Autowired
    private FormRepository formRepository;

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

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
    void getActiveForms_oneAcceptedForm() throws Exception {
        Form form = createSampleForm();
        form.setAvailabilityStart(NOW);
        form.setAvailabilityEnd(NOW.plusDays(2));
        form.setAcceptingPerson(adminAccount);
        formRepository.save(form);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + ACTIVE))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['id']").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['nameOfApplicant']", Matchers.is(NAME_OF_APPLICANT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['city']", Matchers.is(CITY)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['street']", Matchers.is(STREET)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['number']", Matchers.is(NUMBER)));
    }

    @Test
    void getActiveForms_zeroAcceptedForm() throws Exception {
        Form form = createSampleForm();
        formRepository.save(form);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + ACTIVE))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void getFormsToReview_oneFormToReview() throws Exception {
        Form form = createSampleForm();
        form.setAvailabilityStart(null);
        form.setAvailabilityEnd(NOW.plusDays(2));
        form.setAcceptingPerson(null);
        formRepository.save(form);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + QUEUE))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['id']").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['nameOfApplicant']", Matchers.is(NAME_OF_APPLICANT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['city']", Matchers.is(CITY)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['street']", Matchers.is(STREET)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['number']", Matchers.is(NUMBER)));
    }

    @Test
    void getFormsToReview_zeroFormsInQueue() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + QUEUE))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void getFormsToReview_zeroFormsInQueue_oneAcceptedForm() throws Exception {
        Form form = createSampleForm();
        form.setAvailabilityStart(NOW);
        form.setAvailabilityEnd(NOW.plusDays(2));
        form.setAcceptingPerson(adminAccount);
        formRepository.save(form);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + QUEUE))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    void getFormById_existingId() throws Exception {
        Form form = createSampleForm();
        Form savedRecord = formRepository.save(form);

        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + "/" + savedRecord.getId()))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['nameOfApplicant']", Matchers.is(NAME_OF_APPLICANT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['city']", Matchers.is(CITY)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['street']", Matchers.is(STREET)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['number']", Matchers.is(NUMBER)));
    }

    @Test
    @Transactional
    void getFormById_wrongId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(FORMS + "/99999"))
                .andExpect(status().is(404));
    }

    @Test
    void createNewForm() throws Exception {
        FormDao formDao = createSampleFormDao();

        this.mockMvc.perform(MockMvcRequestBuilders.post(FORMS).content(toJson(formDao))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    void deleteForm_idOk() throws Exception {
        Form form = createSampleForm();
        Form savedRecord = formRepository.save(form);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(FORMS + "/" + savedRecord.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteForm_idNotFound() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(FORMS + "/999"))
                .andExpect(status().isNotFound());
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

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
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
