package pl.polsl.AskYourNeighbor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.AskYourNeighbor.model.dao.AdminAccountDao;
import pl.polsl.AskYourNeighbor.service.AdminAccountService;

import java.util.List;

import static pl.polsl.AskYourNeighbor.constant.ResourceUrl.ACCOUNT;

@RestController
@RequestMapping(ACCOUNT)
@CrossOrigin(origins = "http://localhost:4200")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;

    public AdminAccountController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @GetMapping(value = "/{idAdminAccount}")
    @ResponseStatus(HttpStatus.OK)
    public AdminAccountDao getAccountById(@PathVariable Long idAdminAccount) {
        return adminAccountService.findAdminAccountById(idAdminAccount);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminAccountDao> getAllAccounts() {
        return adminAccountService.getAllAccounts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewAccount(@RequestBody AdminAccountDao adminAccountDao) {
        adminAccountService.registerNewAccount(adminAccountDao);
    }

    @DeleteMapping(value = "/{idAdminAccount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long idAdminAccount) {
        adminAccountService.deleteAccount(idAdminAccount);
    }
}
