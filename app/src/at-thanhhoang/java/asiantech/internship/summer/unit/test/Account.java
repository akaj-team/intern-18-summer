package asiantech.internship.summer.unit.test;

public class Account {
    private String username;
    private String password;

    Account() {
    }

    Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
