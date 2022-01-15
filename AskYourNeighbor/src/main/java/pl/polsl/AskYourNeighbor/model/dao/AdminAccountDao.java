package pl.polsl.AskYourNeighbor.model.dao;

import pl.polsl.AskYourNeighbor.model.AdminAccount;

public class AdminAccountDao {

    private Long idAdminAccount;
    private String username;
    private String password;

    public AdminAccountDao() {
    }

    public AdminAccountDao(AdminAccount adminAccount) {
        this.idAdminAccount = adminAccount.getId();
        this.username = adminAccount.getUsername();
        this.password = adminAccount.getPassword();
    }

    public Long getIdAdminAccount() {
        return idAdminAccount;
    }

    public void setIdAdminAccount(Long idAdminAccount) {
        this.idAdminAccount = idAdminAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
