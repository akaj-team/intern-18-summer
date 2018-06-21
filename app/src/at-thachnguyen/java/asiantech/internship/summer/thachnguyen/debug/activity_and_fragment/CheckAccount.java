package asiantech.internship.summer.thachnguyen.debug.activity_and_fragment;

public class CheckAccount {
    public static boolean checkEmail(String email) {
        char start = email.charAt(0);
        if ((start < 65) || (start > 90 && start < 97) || (start > 122)) {
            return false;
        }
        if (!email.contains("@"))
            return false;
        if (email.endsWith("@"))
            return false;
        String temp = email.substring(email.indexOf('@') + 1);
        if (temp.contains("@"))
            return false;
        return true;
}

    public static boolean checkPassword(String password) {
        if (password.length() < 6)
            return false;
        return true;
    }
}

