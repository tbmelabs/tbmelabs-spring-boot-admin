package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.core.authorizationserver.web.signup.SignupController;

public class SignupControllerTest {

  @Mock
  private UserSignupService userSignupServiceFixture;

  @Spy
  @InjectMocks
  private SignupController fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);

    doReturn(true).when(userSignupServiceFixture)
        .isUsernameUnique(ArgumentMatchers.any(User.class));
    doReturn(true).when(userSignupServiceFixture)
        .doesUsernameMatchFormat(ArgumentMatchers.any(User.class));
    doReturn(true).when(userSignupServiceFixture)
        .isEmailAddressUnique(ArgumentMatchers.any(User.class));
    doReturn(true).when(userSignupServiceFixture).isEmailAddress(ArgumentMatchers.any(User.class));
    doReturn(true).when(userSignupServiceFixture)
        .doesPasswordMatchFormat(ArgumentMatchers.any(User.class));
    doReturn(true).when(userSignupServiceFixture)
        .doPasswordsMatch(ArgumentMatchers.any(User.class));
  }

  @Test
  public void signupControllerShouldBeAnnotated() {
    assertThat(SignupController.class).hasAnnotation(RestController.class)
        .hasAnnotation(RequestMapping.class);
    assertThat(SignupController.class.getDeclaredAnnotation(RequestMapping.class).value())
        .containsExactly("/signup");
  }

  @Test
  public void signupControllerShouldHavePublicConstructor() {
    assertThat(new SignupController()).isNotNull();
  }

  @Test
  public void signupShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("signup", new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/do-signup");
  }

  @Test
  public void signupShouldCallServiceMethod() {
    fixture.signup(new User());

    verify(userSignupServiceFixture, times(1)).signUpNewUser(ArgumentMatchers.any(User.class));
  }

  @Test
  public void isUsernameUniqueShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup =
        SignupController.class.getDeclaredMethod("isUsernameUnique", new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/is-username-unique");
  }

  @Test
  public void isUsernameUniqueShouldCallServiceMethod() {
    fixture.isUsernameUnique(new User());

    verify(userSignupServiceFixture, times(1)).isUsernameUnique(ArgumentMatchers.any(User.class));
  }

  @Test
  public void doesUsernameMatchFormatShouldBeAnnotated()
      throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesUsernameMatchFormat",
        new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/does-username-match-format");
  }

  @Test
  public void doesUsernameMatchFormatShouldCallServiceMethod() {
    fixture.doesUsernameMatchFormat(new User());

    verify(userSignupServiceFixture, times(1))
        .doesUsernameMatchFormat(ArgumentMatchers.any(User.class));
  }

  @Test
  public void isEmailAddressUniqueShouldBeAnnotated()
      throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isEmailAddressUnique",
        new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/is-email-unique");
  }

  @Test
  public void isEmailAddressUniqueShouldCallServiceMethod()
      throws NoSuchMethodException, SecurityException {
    fixture.isEmailAddressUnique(new User());

    verify(userSignupServiceFixture, times(1))
        .isEmailAddressUnique(ArgumentMatchers.any(User.class));
  }

  @Test
  public void isEmailAddressShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup =
        SignupController.class.getDeclaredMethod("isEmailAddress", new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/is-email");
  }

  @Test
  public void isEmailAddressShouldCallServiceMethod() {
    fixture.isEmailAddress(new User());

    verify(userSignupServiceFixture, times(1)).isEmailAddress(ArgumentMatchers.any(User.class));
  }

  @Test
  public void doesPasswordMatchFormatShouldBeAnnotated()
      throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesPasswordMatchFormat",
        new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/does-password-match-format");
  }

  @Test
  public void doesPasswordMatchFormatShouldCallServiceMethod() {
    fixture.doesPasswordMatchFormat(new User());

    verify(userSignupServiceFixture, times(1))
        .doesPasswordMatchFormat(ArgumentMatchers.any(User.class));
  }

  @Test
  public void doPasswordsMatchShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method signup =
        SignupController.class.getDeclaredMethod("doPasswordsMatch", new Class<?>[]{User.class});
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value())
        .containsExactly("/do-passwords-match");
  }

  @Test
  public void doPasswordsMatchShouldCallServiceMethod() {
    fixture.doPasswordsMatch(new User());

    verify(userSignupServiceFixture, times(1)).doPasswordsMatch(ArgumentMatchers.any(User.class));
  }
}
