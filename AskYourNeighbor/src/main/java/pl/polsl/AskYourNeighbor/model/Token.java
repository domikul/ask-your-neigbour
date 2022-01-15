package pl.polsl.AskYourNeighbor.model;

import pl.polsl.AskYourNeighbor.model.dao.AdminAccountDao;

import java.util.Date;
import java.util.Objects;

public class Token {
    private String token;

    private Date expirationDate;

    private AdminAccountDao user;

    public Token() {
    }

    public Token(String token, Date expirationDate, AdminAccountDao user) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public AdminAccountDao getUser() {
        return user;
    }

    public void setUser(AdminAccountDao user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token) &&
                Objects.equals(expirationDate, token1.expirationDate) &&
                Objects.equals(user, token1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expirationDate, user);
    }
}
