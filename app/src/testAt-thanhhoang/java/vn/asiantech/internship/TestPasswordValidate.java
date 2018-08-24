package vn.asiantech.internship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unit.test.Account;
import asiantech.internship.summer.unit.test.UtilValidate;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TestPasswordValidate {
    private static final String USERNAME = "gfD1bdDs";
    @Spy
    private Account mPasswordAccount;

    @Test
    public void testCasePasswordDifferentUsername() {
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn(USERNAME).when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_different_username);
    }

    @Test
    public void testCasePassWordLeastTwoSpecialCharacterOrDigit() {
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn("csDvsvDfsv").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_least_two_special_or_digit);
        doReturn("csDvsDvfs%v1").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_least_two_special_or_digit);
    }

    @Test
    public void testCasePasswordLengthAndNotLoopCharacterThanTwoContinue() {
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn("va11fdb").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_length_and_not_repeated_character_than_two_continue);
        doReturn("vaaaa11vfdn").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_length_and_not_repeated_character_than_two_continue);
    }

    @Test
    public void testCasePasswordNotEndDigitOrSpecialCharacter() {
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn("va2aaavfv2").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_not_end_digit_or_special_character);
        doReturn("va2aaavf2d%").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_not_end_digit_or_special_character);
    }

    @Test
    public void testCasePasswordLeastThreeUpperCaseNotContinue() {
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn("vF2vfHd2dv").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_least_three_uppercase_character_not_continue);
        doReturn("vFG2vfHFdd2v").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_password_least_three_uppercase_character_not_continue);
    }

    @Test
    public void testCaseAccountValid(){
        doReturn(USERNAME).when(mPasswordAccount).getUsername();
        doReturn("vF#vBvVvfHd%dv").when(mPasswordAccount).getPassword();
        assertEquals(UtilValidate.isLogin(mPasswordAccount), R.string.check_login_success);
    }
}
