package pl.polsl.AskYourNeighbor.model;

public class Password {
    private final Long id;
    private final String password;

    public Password(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
