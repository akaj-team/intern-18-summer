package asiantech.internship.summer.exercise_fragment_activity;

import java.util.regex.Pattern;

public class CheckValid {
    private static final String  EMAIL_VALID = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]";
    private static final String  PASSWORD_VALID = "[a-zA-Z0-9]";

    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_VALID, Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).find();
    }

    public static boolean isPasswordValid(String password) {
        if(password.length()>=6){
            final Pattern EMAIL_REGEX = Pattern.compile(PASSWORD_VALID, Pattern.CASE_INSENSITIVE);
            return EMAIL_REGEX.matcher(password).find();
        }
        return false;
    }
}
