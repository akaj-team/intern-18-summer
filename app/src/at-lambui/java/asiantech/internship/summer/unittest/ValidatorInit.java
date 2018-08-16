package asiantech.internship.summer.unittest;

import java.util.regex.Pattern;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

public final class ValidatorInit {
    private ValidatorInit() {
    }

    private static final Pattern UserName_Have_Length_From_7_To_21_Char =
            Pattern.compile("^.{7,21}$");
    private static final Pattern UserName_Have_More_Than_2_Digital_Consecutive =
            Pattern.compile("^.*\\d{3,}");
    private static final Pattern UserName_Have_At_Least_2_Char_NonConsecutive =
            Pattern.compile("^.*[A-Z].+[A-Z]");
    private static final Pattern Usernam_Start_With_lowercase =
            Pattern.compile("^[a-z]");
    //password
    private static final Pattern PassWord_Have_Length_Than_7 =
            Pattern.compile("^.{8,}$");
    private static final Pattern PassWord_Have_Repeat_Char =
            Pattern.compile("^.*(.)\\1");
    private static final Pattern PassWord_Not_End_With_Character_Special_0r_Digital =
            Pattern.compile("^.*[a-zA-Z]$");
    private static final Pattern PassWord_Have_At_least_3_Char_NonConsecutive =
            Pattern.compile("^.*[A-Z].+[A-Z].+[A-Z]");
    private static final Pattern PassWord_Have_At_Least_Two_Character_Digital_Or_Special =
            Pattern.compile("^.*[^a-zA-Z].*[^a-zA-Z]");

    /**
     * @param username login
     * @return username true
     */
    public static int validateUserName(String username) {
        if (!UserName_Have_Length_From_7_To_21_Char.matcher(username).find()) {
            return R.string.error_username_length;
        }
        if (!Usernam_Start_With_lowercase.matcher(username).find()) {
            return R.string.error_username_not_start_with_lowercase;
        }
        if (UserName_Have_More_Than_2_Digital_Consecutive.matcher(username).find()) {
            return R.string.error_username_have_not_at_most_two_character_consecutive;
        }
        if (!UserName_Have_At_Least_2_Char_NonConsecutive.matcher(username).find()) {
            return R.string.error_username_have_not_at_least_two_uppercase_nonconsecutive;
        }
        return R.string.username_result;
    }

    /**
     * @param password login
     * @return password true
     */
    public static int validatePassword(String password) {
        if (!PassWord_Have_Length_Than_7.matcher(password).find()) {
            return R.string.error_password_have_not_enough_8_character_char;
        }
        if (PassWord_Have_Repeat_Char.matcher(password).find()) {
            return R.string.error_password_have_character_Which_Repeated;
        }
        if (!PassWord_Have_At_least_3_Char_NonConsecutive.matcher(password).find()) {
            return R.string.error_password_have_at_least_3_character_uppercase_nonconsective;
        }
        if (!PassWord_Not_End_With_Character_Special_0r_Digital.matcher(password).find()) {
            return R.string.error_password_have_not_end_with_digital_or_char;
        }
        if (!PassWord_Have_At_Least_Two_Character_Digital_Or_Special.matcher(password).find()) {
            return R.string.error_password_have_not_at_least_two_character_digital;
        }
        return R.string.password_result;
    }

    /**
     * @param user object
     * @return check username equal password ?
     */
    public static int checkUserName(User user) {
        if (user.getUserName().equals(user.getPassWord())) {
            return R.string.error_password_username_equal;
        }
        return R.string.username_not_equal_password;
    }
}
