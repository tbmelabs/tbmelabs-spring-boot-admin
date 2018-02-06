package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;

public class UserSignupServiceTest {
  private static final String SIGNUP_FAILED_ERROR_MESSAGE = "Registration failed. Please check your details!";

  @Mock
  private UserCRUDRepository userRepositoryFixture;

  @Mock
  private RoleCRUDRepository roleRepositoryFixture;

  @Spy
  @InjectMocks
  private UserSignupService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocation) throws Throwable {
        User newUser = invocation.getArgument(0);
        newUser.setId(new Random().nextLong());
        return newUser;
      }
    }).when(userRepositoryFixture).save(Mockito.any(User.class));

    doReturn(new Role("USER")).when(roleRepositoryFixture).findByName("USER");

    doReturn(true).when(fixture).isUsernameUnique(Mockito.any(User.class));
    doReturn(true).when(fixture).doesUsernameMatchFormat(Mockito.any(User.class));
    doReturn(true).when(fixture).isEmailAddressUnique(Mockito.any(User.class));
    doReturn(true).when(fixture).isEmailAddress(Mockito.any(User.class));
    doReturn(true).when(fixture).doesPasswordMatchFormat(Mockito.any(User.class));
    doReturn(true).when(fixture).doPasswordsMatch(Mockito.any(User.class));
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnUsernameNotUnique() {
    doReturn(false).when(fixture).isUsernameUnique(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnUsernameWrongFormat() {
    doReturn(false).when(fixture).doesUsernameMatchFormat(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnEmailNotUnique() {
    doReturn(false).when(fixture).isEmailAddressUnique(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnEmailWrongFormat() {
    doReturn(false).when(fixture).isEmailAddress(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnPasswordWrongFormat() {
    doReturn(false).when(fixture).doesPasswordMatchFormat(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotSaveUserOnPasswordsDoNotMatch() {
    doReturn(false).when(fixture).doPasswordsMatch(Mockito.any(User.class));

    try {
      fixture.signUpNewUser(new User());
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo(SIGNUP_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void userSignupServiceShouldSaveValidUser() {
    assertThat(fixture.signUpNewUser(new User()).getId()).isNotNull().isOfAnyClassIn(Long.class);
  }
}