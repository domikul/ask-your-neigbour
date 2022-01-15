package pl.polsl.AskYourNeighbor.constant;

public enum ExceptionMessage {

    INCORRECT_REQUEST("Request is incorrect"),
    ACCOUNT_ALREADY_EXISTS("Account with the given username already exists"),
    CATEGORY_DOES_NOT_EXIST("Category doesn't exist"),
    WRONG_UNIT("Wrong unit"),
    WEAK_PASSWORD("Weak password");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
