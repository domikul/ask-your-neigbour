package pl.polsl.AskYourNeighbor.service;

import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.dao.AdminAccountDao;

import java.security.Principal;
import java.util.List;

public interface AdminAccountService {

    AdminAccountDao findAdminAccountById(Long idAccount);

    List<AdminAccountDao> getAllAccounts();

    void registerNewAccount(AdminAccountDao adminAccountDao);

    void deleteAccount(Long idAdminAccount);

    AdminAccount findUserByPrincipal(Principal principal);
}
