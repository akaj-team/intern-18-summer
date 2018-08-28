package asiantech.internship.summer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import asiantech.internship.summer.unittest.helpers.Validator;
import asiantech.internship.summer.unittest.model.User;

import static org.junit.Assert.assertEquals;

public class ValidUsernameTest {
    @Mock
    private User mUser;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void usernameEmpty() {
        Mockito.when(mUser.getUsername()).thenReturn("");
        assertEquals(Validator.validate(mUser), R.string.error_username_empty);
    }

    @Test
    public void usernameNotHave2UpperCase() {
        Mockito.when(mUser.getUsername()).thenReturn("jsahdgkasl");
        assertEquals(Validator.validate(mUser), R.string.error_username_not_has_2_uppercase);
    }

    @Test
    public void usernameHave3AdjacentNumber() {
        Mockito.when(mUser.getUsername()).thenReturn("jsAhd123gkaSl");
        assertEquals(Validator.validate(mUser), R.string.error_username_has_3_adjacent_number);
    }

    @Test
    public void usernameNotStartByNormalChar() {
        Mockito.when(mUser.getUsername()).thenReturn("1jsAhdgkaSl");
        assertEquals(Validator.validate(mUser), R.string.error_username_not_start_with_normal_char);
    }

    @Test
    public void usernameHasLessThan7Char() {
        Mockito.when(mUser.getUsername()).thenReturn("daAsAh");
        assertEquals(Validator.validate(mUser), R.string.error_username_not_has_7_to_21_char);
    }

    @Test
    public void usernameHasMore21Char() {
        Mockito.when(mUser.getUsername()).thenReturn("daAsAhahdajjdasjhgurrenwerhuamewhuvgrnerjndaqeAA");
        assertEquals(Validator.validate(mUser), R.string.error_username_not_has_7_to_21_char);
    }
}
