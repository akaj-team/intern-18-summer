package asiantech.internship.summer.unit_test;

public class Account {
    private String username;
    private String password;

    Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    Account() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
