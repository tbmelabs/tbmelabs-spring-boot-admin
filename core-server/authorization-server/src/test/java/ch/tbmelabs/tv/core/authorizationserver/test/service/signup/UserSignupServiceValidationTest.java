package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;

public class UserSignupServiceValidationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private UserSignupService userSignupService;

  @Autowired
  private TestUserManager testUserManager;

  @Test
  public void userSignupServiceShouldInvalidateExistingUsername() {
    User userWithUnexistingUsername = new User();
    userWithUnexistingUsername.setUsername(RandomStringUtils.randomAlphabetic(11));

    assertThat(userSignupService.isUsernameUnique(testUserManager.getUserUser())).isFalse();
    assertThat(userSignupService.isUsernameUnique(userWithUnexistingUsername)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingUsername() {
    User userWithInvalidUsername = new User();
    userWithInvalidUsername.setUsername(RandomStringUtils.randomAscii(10) + "$");

    User userWithValidUsername = new User();
    userWithValidUsername.setUsername(RandomStringUtils.randomAlphabetic(11));

    assertThat(userSignupService.doesUsernameMatchFormat(userWithInvalidUsername)).isFalse();
    assertThat(userSignupService.doesUsernameMatchFormat(userWithValidUsername)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateExistingEmail() {
    User userWithUnexistingEmail = new User();
    userWithUnexistingEmail.setEmail(RandomStringUtils.randomAlphabetic(11));

    assertThat(userSignupService.isEmailAddressUnique(testUserManager.getUserUser())).isFalse();
    assertThat(userSignupService.isEmailAddressUnique(userWithUnexistingEmail)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingEmail() {
    User userWithInvalidEmail = new User();
    userWithInvalidEmail.setEmail(RandomStringUtils.randomAlphabetic(11));

    User userWithValidEmail = new User();
    userWithValidEmail.setEmail("valid.email@tbme.tv");

    assertThat(userSignupService.isEmailAddress(userWithInvalidEmail)).isFalse();
    assertThat(userSignupService.isEmailAddress(userWithValidEmail)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingPassword() {
    User userWithInvalidPassword = new User();
    userWithInvalidPassword.setPassword(RandomStringUtils.randomAlphabetic(11));

    User userWithValidPassword = new User();
    userWithValidPassword.setPassword("V@l1dP@$$w0rd");

    assertThat(userSignupService.doesPasswordMatchFormat(userWithInvalidPassword)).isFalse();
    assertThat(userSignupService.doesPasswordMatchFormat(userWithValidPassword)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidatePasswordAndConfirmationIfTheyDontMatch() {
    User userWithoutMatchingPasswords = new User();
    userWithoutMatchingPasswords.setPassword("APassword$99");
    userWithoutMatchingPasswords.setConfirmation("NotQuiteAPassword$99");

    User userWithMatchingPasswords = new User();
    userWithMatchingPasswords.setPassword("V@l1dP@$$w0rd");
    userWithMatchingPasswords.setConfirmation(userWithMatchingPasswords.getPassword());
  }
}
