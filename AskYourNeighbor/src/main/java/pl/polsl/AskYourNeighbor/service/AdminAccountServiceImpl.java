package pl.polsl.AskYourNeighbor.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.AskYourNeighbor.constant.ExceptionMessage;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.Password;
import pl.polsl.AskYourNeighbor.model.dao.AdminAccountDao;
import pl.polsl.AskYourNeighbor.repository.AdminAccountRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAccountServiceImpl implements UserDetailsService, AdminAccountService {

    private final AdminAccountRepository adminAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final Gson gson = new Gson();

    public AdminAccountServiceImpl(AdminAccountRepository adminAccountRepository, PasswordEncoder passwordEncoder) {
        this.adminAccountRepository = adminAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminAccountDao findAdminAccountById(Long idAccount) {
        return adminAccountRepository.findById(idAccount).map(AdminAccountDao::new)
                .orElseThrow(() -> new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));
    }

    public List<AdminAccountDao> getAllAccounts() {
        return adminAccountRepository.findAll().stream()
                .map(AdminAccountDao::new)
                .collect(Collectors.toList());
    }

    public void registerNewAccount(AdminAccountDao adminAccountDao) {
        AdminAccount foundedRecord = adminAccountRepository.findByUsername(adminAccountDao.getUsername());
        if (foundedRecord != null && foundedRecord.getUsername().equals(adminAccountDao.getUsername())) {
            throw new IncorrectRequestException(ExceptionMessage.ACCOUNT_ALREADY_EXISTS.getMessage());
        } else {
            try {
                List<String> popularPasswords = getPopularPasswords();
                if (popularPasswords.contains(adminAccountDao.getPassword())) {
                    throw new IncorrectRequestException(ExceptionMessage.WEAK_PASSWORD.getMessage());
                }
            } catch (IOException e) {
            }
            createNewAccount(adminAccountDao);
        }
    }

    private List<String> getPopularPasswords() throws IOException {
        HttpGet request = new HttpGet("http://127.0.0.1:5000/passwords");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Password[] passwords = gson.fromJson(result, Password[].class);
                return Arrays.stream(passwords)
                        .map(p -> p.getPassword())
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    public void deleteAccount(Long idAdminAccount) {
        AdminAccount foundedRecord = adminAccountRepository.findById(idAdminAccount).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));

        adminAccountRepository.delete(foundedRecord);
    }

    @Override
    public AdminAccount findUserByPrincipal(Principal principal) {
        return adminAccountRepository.findByUsername(principal.getName());
    }

    private void createNewAccount(AdminAccountDao adminAccountDao) {
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setUsername(adminAccountDao.getUsername());
        adminAccount.setPassword(passwordEncoder.encode(adminAccountDao.getPassword()));
        adminAccountRepository.save(adminAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminAccount admin = adminAccountRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        return new User(admin.getUsername(), admin.getPassword(), Collections.emptyList());
    }

}
