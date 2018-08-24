package vn.asiantech.internship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.R;
import asiantech.internship.summer.unit.test.Account;
import asiantech.internship.summer.unit.test.UtilValidate;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestUsernameValidate {
    @Mock
    private Account mUsernameAccount;

    @Test
    public void testCaseUsernameLength() {
        when(mUsernameAccount.getUsername()).thenReturn("");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_length);
        when(mUsernameAccount.getUsername()).thenReturn("hvbfhv");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_length);
        when(mUsernameAccount.getUsername()).thenReturn("hvbfhvddvsvfvfdevvsdvw");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_length);
    }

    @Test
    public void testCaseUsernameLeastTwoUppercaseCharacterNotContinue() {
        when(mUsernameAccount.getUsername()).thenReturn("shdshdfbshdj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_least_uppercase_not_continue);
        when(mUsernameAccount.getUsername()).thenReturn("shdhdFBshdj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_least_uppercase_not_continue);
    }

    @Test
    public void testCaseUsernameNotSpecialAndSpaceCharacter() {
        when(mUsernameAccount.getUsername()).thenReturn("sh dsDhdbhdDj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_not_space_and_special_character);
        when(mUsernameAccount.getUsername()).thenReturn("sh%dshDdbhdDj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_not_space_and_special_character);
        when(mUsernameAccount.getUsername()).thenReturn("sh%Ddsh dbhdDj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_not_space_and_special_character);
    }

    @Test
    public void testCaseUsernameMostTwoDigitContinue() {
        when(mUsernameAccount.getUsername()).thenReturn("sh243dDshbhDdj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_most_two_digit_continue);
        when(mUsernameAccount.getUsername()).thenReturn("sh24Dds36hbhDdj");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_most_two_digit_continue);
    }

    @Test
    public void testCaseUsernameNotStartUpperCase() {
        when(mUsernameAccount.getUsername()).thenReturn("Vdh22jsb4Dhds");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_not_start_uppercase_or_digit);
        when(mUsernameAccount.getUsername()).thenReturn("1dDhj22sb4hdDs");
        assertEquals(UtilValidate.isLogin(mUsernameAccount), R.string.check_username_not_start_uppercase_or_digit);
    }
}
