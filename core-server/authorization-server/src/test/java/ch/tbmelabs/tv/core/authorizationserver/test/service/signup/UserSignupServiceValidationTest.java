package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class UserSignupServiceValidationTest {

  private static final UserDTO existingUser = new UserDTO();

  @Mock
  private UserCRUDRepository userRepository;

  @Mock
  private RoleCRUDRepository roleRepository;

  @Spy
  @InjectMocks
  private UserSignupService fixture;

  @BeforeClass
  public static void beforeClassSetUp() {
    existingUser.setUsername(RandomStringUtils.random(11));
    existingUser.setEmail(RandomStringUtils.random(11));
  }

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doAnswer(invocation -> {
      if (invocation.getArgument(0).equals(existingUser.getUsername())) {
        return Optional.of(existingUser);
      }

      return Optional.empty();
    }).when(userRepository).findOneByUsernameIgnoreCase(ArgumentMatchers.anyString());

    doAnswer(invocation -> {
      if (invocation.getArgument(0).equals(existingUser.getEmail())) {
        return Optional.of(existingUser);
      }

      return Optional.empty();
    }).when(userRepository).findOneByEmailIgnoreCase(ArgumentMatchers.anyString());

    doCallRealMethod().when(fixture).isUsernameUnique(ArgumentMatchers.any(UserDTO.class));
    doCallRealMethod().when(fixture).doesUsernameMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doCallRealMethod().when(fixture).isEmailAddressUnique(ArgumentMatchers.any(UserDTO.class));
    doCallRealMethod().when(fixture).isEmailAddress(ArgumentMatchers.any(UserDTO.class));
    doCallRealMethod().when(fixture).doesPasswordMatchFormat(ArgumentMatchers.any(UserDTO.class));
    doCallRealMethod().when(fixture).doPasswordsMatch(ArgumentMatchers.any(UserDTO.class));
  }

  @Test
  public void userSignupServiceShouldInvalidateExistingUsername() {
    final UserDTO unexistingUser = new UserDTO();
    unexistingUser.setUsername(RandomStringUtils.random(11));

    assertThat(fixture.isUsernameUnique(existingUser)).isFalse();
    assertThat(fixture.isUsernameUnique(unexistingUser)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingUsername() {
    final UserDTO invalidUser = new UserDTO();
    invalidUser.setUsername(RandomStringUtils.randomAscii(10) + "$");

    final UserDTO validUser = new UserDTO();
    validUser.setUsername(RandomStringUtils.randomAlphabetic(11));

    assertThat(fixture.doesUsernameMatchFormat(invalidUser)).isFalse();
    assertThat(fixture.doesUsernameMatchFormat(validUser)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateExistingEmail() {
    UserDTO unexistingUser = new UserDTO();
    unexistingUser.setEmail(RandomStringUtils.random(11));

    assertThat(fixture.isEmailAddressUnique(existingUser)).isFalse();
    assertThat(fixture.isEmailAddressUnique(unexistingUser)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingEmail() {
    UserDTO invalidUser = new UserDTO();
    invalidUser.setEmail(RandomStringUtils.random(11));

    UserDTO validUser = new UserDTO();
    validUser.setEmail("valid.email@tbme.tv");

    assertThat(fixture.isEmailAddress(invalidUser)).isFalse();
    assertThat(fixture.isEmailAddress(validUser)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidateNotMatchingPassword() {
    UserDTO invalidUser = new UserDTO();
    invalidUser.setPassword(RandomStringUtils.random(11));

    UserDTO validUser = new UserDTO();
    validUser.setPassword("V@l1dP@$$w0rd");

    assertThat(fixture.doesPasswordMatchFormat(invalidUser)).isFalse();
    assertThat(fixture.doesPasswordMatchFormat(validUser)).isTrue();
  }

  @Test
  public void userSignupServiceShouldInvalidatePasswordAndConfirmationIfTheyDontMatch() {
    UserDTO invalidUser = new UserDTO();
    invalidUser.setPassword("APassword$99");
    invalidUser.setConfirmation("NotQuiteAPassword$99");

    UserDTO validUser = new UserDTO();
    validUser.setPassword("V@l1dP@$$w0rd");
    validUser.setConfirmation(validUser.getPassword());

    assertThat(fixture.doPasswordsMatch(invalidUser)).isFalse();
    assertThat(fixture.doPasswordsMatch(validUser)).isTrue();
  }
}
