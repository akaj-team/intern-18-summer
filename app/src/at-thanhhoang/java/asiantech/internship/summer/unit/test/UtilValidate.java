package asiantech.internship.summer.unit.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import asiantech.internship.summer.R;

public final class UtilValidate {
    private static Pattern mRegex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

    private UtilValidate() {
    }

    private static boolean isUsernameLength(String username) {
        return username.length() > 6 && username.length() < 22;
    }

    private static boolean isUsernameLeastTwoUppercaseCharacterNotContinue(String username, int numberUppercase) {
        int usernameSize = username.length();
        List<Integer> arrPosition = new ArrayList<>();
        for (int i = 0; i < usernameSize; i++) {
            if (Character.isUpperCase(username.charAt(i))) {
                arrPosition.add(i);
            }
        }
        int positionSize = arrPosition.size();
        if (positionSize >= numberUppercase) {
            for (int i = 0; i < positionSize - 1; i++) {
                if (arrPosition.get(i + 1) - arrPosition.get(i) == 1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isUsernameNotSpecialAndSpaceCharacter(String username) {
        return !username.contains(" ") && !mRegex.matcher(username).find();
    }

    private static boolean isUsernameMostTwoDigitContinue(String username) {
        int count = 0;
        int usernameSize = username.length();
        List<Integer> arrPosition = new ArrayList<>();
        for (int i = 0; i < usernameSize; i++) {
            if (Character.isDigit(username.charAt(i))) {
                arrPosition.add(i);
            }
        }

        int positionSize = arrPosition.size();
        if (positionSize <= 2) {
            return true;
        } else {
            for (int i = 0; i < positionSize - 1; i++) {
                if (arrPosition.get(i + 1) - arrPosition.get(i) == 1) {
                    count++;
                }
            }
            return count <= 1;
        }
    }

    private static boolean isUsernameNotStartWithUpperCaseOrDigit(String username) {
        char c = username.charAt(0);
        return !Character.isUpperCase(c) && !Character.isDigit(c);
    }

    private static boolean isPasswordDifferentUsername(String password, String username) {
        return !password.equals(username);
    }

    private static boolean isPasswordLeastTwoSpecialCharacter(String password) {
        int passwordSize = password.length();
        int count = 0;
        for (int i = 0; i < passwordSize; i++) {
            if (mRegex.matcher(String.valueOf(password.charAt(i))).find()) {
                count++;
            }
        }
        return count >= 2;
    }

    private static boolean isPasswordLeastTwoDigit(String password) {
        int passwordSize = password.length();
        int count = 0;
        for (int i = 0; i < passwordSize; i++) {
            if (Character.isDigit(password.charAt(i))) {
                count++;
            }
        }
        return (count >= 2);
    }

    private static boolean isPasswordLeastTwoSpecialCharacterOrDigit(String password) {
        return isPasswordLeastTwoSpecialCharacter(password) || isPasswordLeastTwoDigit(password);
    }

    private static boolean isPasswordLengthAndNotLoopCharacterThanTwoContinue(String password) {
        int passwordSize = password.length();
        int count = 0;
        if (passwordSize >= 8) {
            for (int i = 0; i < passwordSize - 1; i++) {
                if ((password.charAt(i) == password.charAt(i + 1))) {
                    count++;
                    if (count > 2) {
                        return false;
                    }
                } else {
                    count = 0;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isPasswordNotEndSpecialCharacterOrDigit(String password) {
        int endPosition = password.length() - 1;
        char c = password.charAt(endPosition);
        return !mRegex.matcher(String.valueOf(c)).find() && !Character.isDigit(c);
    }

    private static boolean isPasswordLeastThreeUpperCaseNotContinue(String password) {
        return isUsernameLeastTwoUppercaseCharacterNotContinue(password, 3);
    }

    public static int isLogin(Account account) {
        if (!isUsernameLength(account.getUsername())) {
            return R.string.check_username_length;
        }
        if (!isUsernameLeastTwoUppercaseCharacterNotContinue(account.getUsername(), 2)) {
            return R.string.check_username_least_uppercase_not_continue;
        }
        if (!isUsernameNotSpecialAndSpaceCharacter(account.getUsername())) {
            return R.string.check_username_not_space_and_special_character;
        }

        if (!isUsernameMostTwoDigitContinue(account.getUsername())) {
            return R.string.check_username_most_two_digit_continue;
        }
        if (!isUsernameNotStartWithUpperCaseOrDigit(account.getUsername())) {
            return R.string.check_username_not_start_uppercase_or_digit;
        }

        if (!isPasswordDifferentUsername(account.getPassword(), account.getUsername())) {
            return R.string.check_password_different_username;
        }
        if (!isPasswordLeastTwoSpecialCharacterOrDigit(account.getPassword())) {
            return R.string.check_password_least_two_special_or_digit;
        }
        if (!isPasswordLengthAndNotLoopCharacterThanTwoContinue(account.getPassword())) {
            return R.string.check_password_length_and_not_repeated_character_than_two_continue;
        }
        if (!isPasswordNotEndSpecialCharacterOrDigit(account.getPassword())) {
            return R.string.check_password_not_end_digit_or_special_character;
        }
        if (!isPasswordLeastThreeUpperCaseNotContinue(account.getPassword())) {
            return R.string.check_password_least_three_uppercase_character_not_continue;
        }
        return R.string.check_login_success;
    }
}
