package asiantech.internship.summer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import asiantech.internship.summer.unittest.ValidatorInit;
import asiantech.internship.summer.unittest.model.User;

import static junit.framework.Assert.assertEquals;

public class UsernameTest {
    @Mock
    private User mUser;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void usernameHaveLengthlessThan7char() {
        Mockito.when(mUser.getUserName()).thenReturn("abac1");
        Assert.assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_length);
    }

    @Test
    public void usernameHaveLengthMoreThan22Char() {
        Mockito.when(mUser.getUserName()).thenReturn("abacdef1ghkiutlsyladdferhop");
        assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_length);
    }

    @Test
    public void userNameStartWithLowercase() {
        Mockito.when(mUser.getUserName()).thenReturn("1abdfaaaa");
        assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_not_start_with_lowercase);
    }

    @Test
    public void userNameStartWithDigital() {
        Mockito.when(mUser.getUserName()).thenReturn("1ghksfyj4");
        assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_not_start_with_lowercase);
    }

    @Test
    public void userNameHaveAtMost2DigitalConsecutive() {
        Mockito.when(mUser.getUserName()).thenReturn("abcd123fgekj");
        assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_have_not_at_most_two_character_consecutive);
    }

    @Test
    public void userNameHaveAtLeast2CharNonConsecutive() {
        Mockito.when(mUser.getUserName()).thenReturn("abcd1fgekj");
        assertEquals(ValidatorInit.validateUserName(mUser.getUserName()), R.string.error_username_have_not_at_least_two_uppercase_nonconsecutive);
    }
}
