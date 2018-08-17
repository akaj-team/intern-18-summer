package asiantech.internship.summer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import asiantech.internship.summer.unit_test.Account;
import asiantech.internship.summer.unit_test.UtilValidate;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountValidationTest {
    @Mock
    private Account mAccountUsername;
    @Spy
    private Account mAccountPassword;

    @Test
    public void testCaseLengthUser() {
        when(mAccountUsername.getUsername()).thenReturn("");
        assertFalse(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("asdsdf");
        assertTrue(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjghgjghjtjghj");
        assertTrue(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjghgjghjtjghjd");
        assertFalse(UtilValidate.isLengthUsernameRight(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseContainUpcase() {
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjghgjghjd");
        assertEquals(UtilValidate.containUpcase(mAccountUsername.getUsername()), 0);
    }

    @Test
    public void testCaseUpcaseIsNotContinous() {
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjghgjggh");
        assertFalse(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjgjgTTth");
        assertFalse(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdhgjgTfTth");
        assertTrue(UtilValidate.isUpcaseNotContinous(mAccountUsername.getUsername(), 2));
    }

    @Test
    public void testCaseUsernameisNotSpecialCharacter() {
        when(mAccountUsername.getUsername()).thenReturn("shddfhf_jd");
        assertFalse(UtilValidate.isUsernameNotSpecialCharacter(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("shddfhfjd");
        assertTrue(UtilValidate.isUsernameNotSpecialCharacter(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseUsernameIsContainDigit() {
        when(mAccountUsername.getUsername()).thenReturn("asdsdfdjghjd");
        assertEquals(UtilValidate.usernameContainNumOfDigit(mAccountUsername.getUsername()), 0);
    }

    @Test
    public void testCaseUsernameIsDigitContinous() {
        when(mAccountUsername.getUsername()).thenReturn("dfifids");
        assertTrue(UtilValidate.isDigitUsernameContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("dfifid5s6j7");
        assertFalse(UtilValidate.isDigitUsernameContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("dfifid5f6j");
        assertFalse(UtilValidate.isDigitUsernameContinous(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("dfifid56j");
        assertTrue(UtilValidate.isDigitUsernameContinous(mAccountUsername.getUsername()));
    }

    @Test
    public void testCaseUsernameStartLowerCase() {
        when(mAccountUsername.getUsername()).thenReturn("dfifid56j");
        assertTrue(UtilValidate.isUsernameStartLowerCase(mAccountUsername.getUsername()));
        when(mAccountUsername.getUsername()).thenReturn("Aajhshssk");
        assertFalse(UtilValidate.isUsernameStartLowerCase(mAccountUsername.getUsername()));
    }

    @Test
    public void testCasePasswordIsNotUser() {
        when(mAccountUsername.getUsername()).thenReturn("ajhd");
        doReturn("dfgh").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordNotUser(mAccountUsername.getUsername(), mAccountPassword.getPassword()));
        when(mAccountUsername.getUsername()).thenReturn("fghj");
        doReturn("fghj").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordNotUser(mAccountUsername.getUsername(), mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordContainsDigitAndSpecial() {
        doReturn("fghj").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordContainsDigitAndSpecial(mAccountPassword.getPassword()));
        doReturn("fgh)1h").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordContainsDigitAndSpecial(mAccountPassword.getPassword()));
    }

    @Test
    public void testCaseCheckLengthPassword() {
        doReturn("asdsd").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isLengthPasswordRight(mAccountPassword.getPassword()));
        doReturn("asdsdhtf").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isLengthPasswordRight(mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordNotRepeatCharacter() {
        doReturn("asddsd").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordNotRepeatCharacter(mAccountPassword.getPassword()));
        doReturn("asdsdhsa").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordNotRepeatCharacter(mAccountPassword.getPassword()));
    }

    @Test
    public void testCasePasswordEndWithLetter() {
        doReturn("asajhjd1").when(mAccountPassword).getPassword();
        assertFalse(UtilValidate.isPasswordEndWithLetter(mAccountPassword.getPassword()));
        doReturn("dhdhdf").when(mAccountPassword).getPassword();
        assertTrue(UtilValidate.isPasswordEndWithLetter(mAccountPassword.getPassword()));
    }

    @Test
    public void testCaseResultLogin() {
        when(mAccountUsername.getUsername()).thenReturn("fg");
        doReturn("jfgd").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHECK_LENGTH_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn("djfaah");
        doReturn("jsgd").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.UPCASE_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn("djfHiH_h");
        doReturn("jdry").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.USERNAME_IS_NOT_SPECIAL);
        when(mAccountUsername.getUsername()).thenReturn("djfHiH345h");
        doReturn("jdtt").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.USERNAME_CONTAIN_DIGIT);
        when(mAccountUsername.getUsername()).thenReturn("AjfHiH35h");
        doReturn("dfiy").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.START_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn("jfHiH34h");
        doReturn("asdf").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHECK_LENGTH_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn("jfHiH35y");
        doReturn("jfHiH35y").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.PASSWORD_IS_NOT_USERNAME);
        when(mAccountUsername.getUsername()).thenReturn("jfHiK35h");
        doReturn("jfHisfg5h").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.PASSWORD_CONTAIN_SPECIAL);
        when(mAccountUsername.getUsername()).thenReturn("jfMiH35h");
        doReturn("jfHiKss9sHfg5h").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.CHARACRER_PASSWORD_REPEAT);
        when(mAccountUsername.getUsername()).thenReturn("jfHkH35h");
        doReturn("jfHis6g5h5").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.END_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn("jsHiH35h");
        doReturn("jfHis6g5hH").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.UPCASE_PASSWORD);
        when(mAccountUsername.getUsername()).thenReturn("jeHiH35h");
        doReturn("jfHisK6g5hH").when(mAccountPassword).getPassword();
        assertEquals(UtilValidate.resultLogin(mAccountUsername.getUsername(), mAccountPassword.getPassword()), UtilValidate.SUCCESS);
    }
}
