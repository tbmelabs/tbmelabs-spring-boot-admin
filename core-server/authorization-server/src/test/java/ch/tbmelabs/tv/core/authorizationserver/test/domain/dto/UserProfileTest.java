package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;

public class UserProfileTest {
  @Spy
  private UserProfile fixture;

  private final User testUser = createTestUser();

  public static User createTestUser() {
    User user = new User();
    user.setCreated(new Date());
    user.setLastUpdated(new Date());
    user.setId(new Random().nextLong());
    user.setUsername(RandomStringUtils.random(11));
    user.setEmail(user.getUsername() + "@tbme.tv");
    user.setPassword(RandomStringUtils.random(60));
    user.setConfirmation(user.getPassword());
    user.setIsEnabled(true);
    user.setIsBlocked(false);
    user.setEmailConfirmationToken(new EmailConfirmationToken(RandomStringUtils.random(36), user));

    Role testRole = new Role("TEST_ROLE");
    testRole.setId(new Random().nextLong());
    user.setRoles(Arrays.asList(user.roleToAssociation(testRole)));

    return user;
  }

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();
  }

  @Test
  public void userProfileShouldHaveNoArgsConstructor() {
    assertThat(new UserProfile()).isNotNull();
  }

  @Test
  public void userProfileShouldHaveAllArgsConstructor() {
    List<Role> roles = testUser.getRoles().stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList());

    assertThat(new UserProfile(testUser, roles)).hasFieldOrPropertyWithValue("created", testUser.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testUser.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testUser.getId())
        .hasFieldOrPropertyWithValue("username", testUser.getUsername())
        .hasFieldOrPropertyWithValue("email", testUser.getEmail())
        .hasFieldOrPropertyWithValue("isEnabled", testUser.getIsEnabled())
        .hasFieldOrPropertyWithValue("isBlocked", testUser.getIsBlocked()).hasFieldOrPropertyWithValue("roles", roles);
  }

  @Test
  public void userProfileShouldHaveIdGetterAndSetter() {
    User user = Mockito.mock(User.class, Mockito.CALLS_REAL_METHODS);
    Long id = new Random().nextLong();

    user.setId(id);

    assertThat(user).hasFieldOrPropertyWithValue("id", id);
    assertThat(user.getId()).isEqualTo(id);
  }

  @Test
  public void userProfileShouldHaveUsernameGetterAndSetter() {
    String username = RandomStringUtils.random(11);

    fixture.setUsername(username);

    assertThat(fixture).hasFieldOrPropertyWithValue("username", username);
    assertThat(fixture.getUsername()).isEqualTo(username);
  }

  @Test
  public void userProfileShouldHaveEmailGetterAndSetter() {
    String email = RandomStringUtils.random(11);

    fixture.setEmail(email);

    assertThat(fixture).hasFieldOrPropertyWithValue("email", email);
    assertThat(fixture.getEmail()).isEqualTo(email);
  }

  @Test
  public void userProfileShouldHavePasswordGetterAndSetter() {
    String password = RandomStringUtils.random(11);

    fixture.setPassword(password);

    assertThat(fixture).hasFieldOrPropertyWithValue("password", password);
    assertThat(fixture.getPassword()).isEqualTo(password);
  }

  @Test
  public void userProfileShouldHaveConfirmationGetterAndSetter() {
    String confirmation = RandomStringUtils.random(11);

    fixture.setConfirmation(confirmation);

    assertThat(fixture).hasFieldOrPropertyWithValue("confirmation", confirmation);
    assertThat(fixture.getConfirmation()).isEqualTo(confirmation);
  }

  @Test
  public void userProfileShouldHaveRoleGetterAndSetter() {
    List<Role> roles = testUser.getRoles().stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList());

    fixture.setRoles(roles);

    assertThat(fixture).hasFieldOrPropertyWithValue("roles", roles);
    assertThat(fixture.getRoles()).isEqualTo(roles);
  }
}