package pl.perspektywy.AskYourNeighbor.controller;

import com.google.gson.Gson;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.perspektywy.AskYourNeighbor.model.AdminAccount;
import pl.perspektywy.AskYourNeighbor.repository.AdminAccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.perspektywy.AskYourNeighbor.constant.ResourceUrl.ACCOUNT;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminAccountControllerTest {

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        adminAccountRepository.deleteAll();
    }

    @Test
    @Transactional
    void getAccountById_existingId() throws Exception {
        AdminAccount adminAccount = createSampleAdminAccount();
        AdminAccount savedRecord = adminAccountRepository.save(adminAccount);

        this.mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT + "/" + savedRecord.getId()))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['idAdminAccount']", Matchers.is(savedRecord.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['username']", Matchers.is(USERNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.['password']", Matchers.is(PASSWORD)));
    }

    @Test
    @Transactional
    void getFormById_wrongId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT + "/99999"))
                .andExpect(status().is(404));
    }

    @Test
    void getAccounts() throws Exception {
        AdminAccount adminAccount = createSampleAdminAccount();
        AdminAccount savedRecord = adminAccountRepository.save(adminAccount);

        this.mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['idAdminAccount']", Matchers.is(savedRecord.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['username']", Matchers.is(USERNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].['password']", Matchers.is(PASSWORD)));
    }

    @Test
    void createNewAccount() throws Exception {
        AdminAccount adminAccount = createSampleAdminAccount();

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post(ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new Gson().toJson(adminAccount)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(result).isNotNull();
    }

    @Test
    void deleteAccountById() throws Exception {
        AdminAccount adminAccount = createSampleAdminAccount();
        AdminAccount savedAccount = adminAccountRepository.save(adminAccount);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.delete(ACCOUNT + "/" + savedAccount.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(result).isNotNull();
    }


    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    private AdminAccount createSampleAdminAccount() {
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setUsername(USERNAME);
        adminAccount.setPassword(PASSWORD);
        return adminAccount;
    }
}
