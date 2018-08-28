package asiantech.internship.summer.unittest.helpers;

import java.util.regex.Pattern;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unittest.model.User;

public final class Validator {

    private Validator() {
        //no-op
    }

    private static final Pattern VALID_USERNAME_HAS_2_UPPERCASE_REGEX =
            Pattern.compile("^.*[A-Z].+[A-Z]");
    private static final Pattern VALID_USERNAME_HAS_3_NUMBER_ADJACENT_REGEX =
            Pattern.compile("^.*\\d{3,}");
    private static final Pattern VALID_USERNAME_START_BY_NORMAL_CHAR_REGEX =
            Pattern.compile("^[a-z]");
    private static final Pattern VALID_USERNAME_HAS_7_TO_21_CHAR_REGEX =
            Pattern.compile("^[a-zA-Z\\d]{7,21}$");

    private static final Pattern VALID_PASSWORD_REPEAT_CHAR_REGEX =
            Pattern.compile("^.*(.)\\1");
    private static final Pattern VALID_PASSWORD_HAS_2_SPECIAL_CHAR_REGEX =
            Pattern.compile("^.*[^a-zA-Z].*[^a-zA-Z]");
    private static final Pattern VALID_PASSWORD_HAS_3_UPPERCASE_CHAR_REGEX =
            Pattern.compile("^.*[A-Z].+[A-Z].+[A-Z]");
    private static final Pattern VALID_PASSWORD_HAS_AT_LEAST_8_CHAR_REGEX =
            Pattern.compile("^.{8,}$");
    private static final Pattern VALID_PASSWORD_NOT_END_WITH_SPECIAL_CHAR_REGEX =
            Pattern.compile("^.*[a-z]$");

    /**
     * @param user user's information including password and username
     * @return message for user
     */
    public static int validate(User user) {
        if (user.getUsername().isEmpty()) {
            return R.string.error_username_empty;
        }

        if (!VALID_USERNAME_HAS_2_UPPERCASE_REGEX.matcher(user.getUsername()).find()) {
            return R.string.error_username_not_has_2_uppercase;
        }
        if (VALID_USERNAME_HAS_3_NUMBER_ADJACENT_REGEX.matcher(user.getUsername()).find()) {
            return R.string.error_username_has_3_adjacent_number;
        }
        if (!VALID_USERNAME_START_BY_NORMAL_CHAR_REGEX.matcher(user.getUsername()).find()) {
            return R.string.error_username_not_start_with_normal_char;
        }
        if (!VALID_USERNAME_HAS_7_TO_21_CHAR_REGEX.matcher(user.getUsername()).find()) {
            return R.string.error_username_not_has_7_to_21_char;
        }

        if (user.getPassword().isEmpty()) {
            return R.string.error_username_empty;
        }

        if (user.getUsername().equals(user.getPassword())) {
            return R.string.error_username_equals_password;
        }
        if (VALID_PASSWORD_REPEAT_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.error_password_repeat_char;
        }
        if (!VALID_PASSWORD_HAS_2_SPECIAL_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.error_password_not_has_2_special_char;
        }
        if (!VALID_PASSWORD_HAS_3_UPPERCASE_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.error_password_not_has_3_uppercase;
        }
        if (!VALID_PASSWORD_HAS_AT_LEAST_8_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.error_password_has_less_than_8_char;
        }
        if (!VALID_PASSWORD_NOT_END_WITH_SPECIAL_CHAR_REGEX.matcher(user.getPassword()).find()) {
            return R.string.error_password_end_with_special_char;
        }
        return R.string.valid_username_password;
    }
}
