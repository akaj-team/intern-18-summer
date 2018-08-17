package asiantech.internship.summer.unit_test;

public final class UtilValidate {
    public final static String CHECK_LENGTH_USERNAME = "Username must be 6 from 22 characters";
    public final static String UPCASE_USERNAME = "Username must have at least 2 non consecutive capital character";
    public final static String USERNAME_IS_NOT_SPECIAL = "Username isn't special character and space";
    public final static String USERNAME_CONTAIN_DIGIT = "Username must be many 2 next digit";
    public final static String START_USERNAME = "Username can't begin with capital character or digit ";
    public final static String PASSWORD_IS_NOT_USERNAME = "Password must be different username";
    public final static String PASSWORD_CONTAIN_SPECIAL = "Password is at least 2 special character or digit";
    public final static String CHECK_LENGTH_PASSWORD = "Password must be 8 or more characters";
    public final static String CHARACRER_PASSWORD_REPEAT = "Password isn't loop 1 character too 2 times next";
    public final static String END_PASSWORD = "Password can't finish with special character or digit ";
    public final static String UPCASE_PASSWORD = "Password must have at least 3 non consecutive capital character";
    public final static String SUCCESS = "Success!!!";

    private UtilValidate() {
    }

    /**
     * this method is used to check length username is from 6 to 22 character
     *
     * @param username is name of user which need to be checked
     * @return value was checked
     */
    public static boolean isLengthUsernameRight(String username) {
        return username.length() >= 6 && username.length() <= 22;
    }

    /**
     * this method is user to return number of capital character is contained in a string
     *
     * @param string is string to check how many capital character in it
     * @return number of capital character is contained in string
     */
    public static int containUpcase(String string) {
        int n = 0;
        int size = string.length();
        for (int i = 0; i < size; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    /**
     * this method is used to check capital character isn't continous and check at least capital character is contained
     *
     * @param string is string need to be checked
     * @param number is at least number of capital character
     * @return value was checked
     */
    public static boolean isUpcaseNotContinous(String string, int number) {
        if (containUpcase(string) < number) {
            return false;
        }
        int m = -2;
        int n;
        int size = string.length();
        for (int i = 0; i < size; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                n = i;
                if (m + 1 == n) {
                    return false;
                }
                m = n;
            }
        }
        return true;
    }

    /**
     * This method is used to check username doesn't contain special character
     *
     * @param username is string user name, which is checked
     * @return value was checked
     */
    public static boolean isUsernameNotSpecialCharacter(String username) {
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to find number of digit in username
     *
     * @param username is string to find number of digit
     * @return number digit is contain username
     */
    public static int usernameContainNumOfDigit(String username) {
        int n = 0;
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (Character.isDigit(username.charAt(i))) {
                n++;
            }
        }
        return n;
    }

    /**
     * This method is used to check username contain many 2 next digit or less than 2 digit
     *
     * @param username is string, which is checked
     * @return value was checked
     */
    public static boolean isDigitUsernameContinous(String username) {
        if (usernameContainNumOfDigit(username) <= 1) {
            return true;
        }
        if (usernameContainNumOfDigit(username) > 2) {
            return false;
        }
        int m = -2;
        int n;
        int size = username.length();
        for (int i = 0; i < size; i++) {
            if (Character.isDigit(username.charAt(i))) {
                n = i;
                if (m + 1 == n) {
                    return true;
                }
                m = n;
            }
        }
        return false;
    }

    /**
     * This method is used to check username is started with lower case
     *
     * @param username is string, which is checked
     * @return value was checked
     */
    public static boolean isUsernameStartLowerCase(String username) {
        return Character.isLowerCase(username.charAt(0));
    }

    /**
     * This method is used to check password isn't username
     *
     * @param username is username string, which is compared to password
     * @param password is password string, which is compared to username
     * @return value was checked
     */
    public static boolean isPasswordNotUser(String username, String password) {
        return username.length() != password.length() || !password.contains(username);
    }

    /**
     * This method is used to check password contain at least 2 digit and special character
     *
     * @param password is string, which is checked condition
     * @return value was checked
     */
    public static boolean isPasswordContainsDigitAndSpecial(String password) {
        int n = 0;
        int size = password.length();
        for (int i = 0; i < size; i++) {
            if ((!Character.isLetterOrDigit(password.charAt(i))
                    && !Character.isSpaceChar(password.charAt(i))
                    || Character.isDigit(password.charAt(i)))) {
                n++;
            }
        }
        return n >= 2;
    }

    /**
     * This method is checked password length more than 8 character
     *
     * @param password is string, which is checked
     * @return value was checked
     */
    public static boolean isLengthPasswordRight(String password) {
        return password.length() >= 8;
    }

    /**
     * This method is used to check 2 next character in password isn't repeat
     *
     * @param password is string, which is checked
     * @return value was checked
     */
    public static boolean isPasswordNotRepeatCharacter(String password) {
        int size = password.length();
        for (int i = 0; i < size - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to check password is ended with a letter
     *
     * @param password is string, which is checked
     * @return value was checked
     */
    public static boolean isPasswordEndWithLetter(String password) {
        return Character.isLetter(password.charAt(password.length() - 1));
    }

    /**
     * This method is used to return a notification about password and username are valid or invalid
     *
     * @param username is username string, which is checked valid or invalid
     * @param password is password string, which is checked valid or invalid
     * @return notification string
     */
    public static String resultLogin(String username, String password) {
        if (!isLengthUsernameRight(username)) {
            return CHECK_LENGTH_USERNAME;
        }
        if (!isUpcaseNotContinous(username, 2)) {
            return UPCASE_USERNAME;
        }
        if (!isUsernameNotSpecialCharacter(username)) {
            return USERNAME_IS_NOT_SPECIAL;
        }
        if (!isDigitUsernameContinous(username)) {
            return USERNAME_CONTAIN_DIGIT;
        }
        if (!isUsernameStartLowerCase(username)) {
            return START_USERNAME;
        }
        if (!isLengthPasswordRight(password)) {
            return CHECK_LENGTH_PASSWORD;
        }
        if (!isPasswordNotUser(username, password)) {
            return PASSWORD_IS_NOT_USERNAME;
        }
        if (!isPasswordContainsDigitAndSpecial(password)) {
            return PASSWORD_CONTAIN_SPECIAL;
        }
        if (!isPasswordNotRepeatCharacter(password)) {
            return CHARACRER_PASSWORD_REPEAT;
        }
        if (!isPasswordEndWithLetter(password)) {
            return END_PASSWORD;
        }
        if (!isUpcaseNotContinous(password, 3)) {
            return UPCASE_PASSWORD;
        }
        return SUCCESS;
    }
}
