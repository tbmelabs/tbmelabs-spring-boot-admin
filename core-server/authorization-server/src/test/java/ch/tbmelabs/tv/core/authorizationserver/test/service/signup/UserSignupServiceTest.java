package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Transactional
public class UserSignupServiceTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String REGISTRATION_FAILED_ERROR_MESSAGE = "Registration failed. Please check your details!";

  @Autowired
  private UserSignupService userSignupService;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private TestUserManager testUserManager;

  @Test(expected = IllegalArgumentException.class)
  public void userSignupServiceShouldNotRegisterAlreadyExistingUser() {
    try {
      userSignupService.signUpNewUser(testUserManager.getUserUser());
    } catch (IllegalArgumentException e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class).hasMessage(REGISTRATION_FAILED_ERROR_MESSAGE);

      throw e;
    }
  }

  @Test
  public void userSignupServiceShouldRegisterNewUser() {
    User newUser = new User();
    newUser.setUsername("NewUsername");
    newUser.setEmail("new.user@tbme.tv");
    newUser.setPassword("Password$99");
    newUser.setConfirmation("Password$99");

    User createdUser = userSignupService.signUpNewUser(newUser);

    assertThat(userRepository.findByUsername(createdUser.getUsername())).isNotNull();
    assertThat(createdUser.getGrantedAuthorities().stream().map(UserRoleAssociation::getUserRole).map(Role::getName)
        .collect(Collectors.toList())).isNotNull().isNotEmpty().hasSize(1).containsExactly(SecurityRole.USER);
  }
}