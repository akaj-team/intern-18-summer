package asiantech.internship.summer.activity_fragment.model;

public class Account {
    private String password;
    private String email;
    private String info;

    public Account(String email, String password, String info) {
        this.password = password;
        this.email = email;
        this.info = info;
    }

    public boolean check(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public String getInfo() {
        return info;
    }
}
