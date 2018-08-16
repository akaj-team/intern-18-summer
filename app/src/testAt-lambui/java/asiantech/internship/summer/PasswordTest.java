package asiantech.internship.summer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import asiantech.internship.summer.unittest.ValidatorInit;
import asiantech.internship.summer.unittest.model.User;

import static junit.framework.Assert.assertEquals;

public class PasswordTest {
    @Spy
    private User mUser;

    @Before
    public void init() {
        mUser = Mockito.spy(new User("DucLam09Tk", ""));
    }

    @Test
    public void passWordHaveLengthThan7() {
        Mockito.doReturn("LamBui").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.error_password_have_not_enough_8_character_char);
    }

    @Test
    public void passWordHaveCharacterRepeatMore() {
        Mockito.doReturn("LamBui1233Tk").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.error_password_have_character_Which_Repeated);
    }

    // at least two character digital
    @Test
    public void passWordHaveAtLeastTwoCharacterDigitalOrSpecial() {
        Mockito.doReturn("LamBuiTk").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.error_password_have_not_at_least_two_character_digital);
    }

    @Test
    public void passWordHaveAtLeast3CharNonConsecutive() {
        Mockito.doReturn("LamBui123tk").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.error_password_have_at_least_3_character_uppercase_nonconsective);
    }

    @Test
    public void passWordNotEndWithCharacterSpecial0rDigital() {
        Mockito.doReturn("LamBui13TK3").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.error_password_have_not_end_with_digital_or_char);
    }

    @Test
    public void passwordAndUsernameEqual() {
        Mockito.doReturn("DucLam09Tk").when(mUser).getPassWord();
        assertEquals(ValidatorInit.checkUserName(mUser), R.string.error_password_username_equal);
    }

    @Test
    public void passwordAndUsernameNotEqual() {
        Mockito.doReturn("DucLam0Tk").when(mUser).getPassWord();
        assertEquals(ValidatorInit.checkUserName(mUser), R.string.username_not_equal_password);
    }

    @Test
    public void passwordRight() {
        Mockito.doReturn("LamBui123TK").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validatePassword(mUser.getPassWord()), R.string.password_result);
    }

    @Test
    public void usernameRight() {
        Mockito.doReturn("ducLam09TK").when(mUser).getPassWord();
        assertEquals(ValidatorInit.validateUserName(mUser.getPassWord()), R.string.username_result);
    }
}
