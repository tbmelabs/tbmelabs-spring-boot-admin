package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

public class UserTest {

  private static final String TEST_USER_ROLE = RandomStringUtils.random(11);

  @Spy
  private User fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();
  }

  @Test
  public void userShouldBeAnnotated() {
    assertThat(User.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(User.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("users");
  }

  @Test
  public void userShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(User.class);
  }

  @Test
  public void userShouldHaveNoArgsConstructor() {
    assertThat(new User()).isNotNull();
  }

  @Test
  public void newUserShouldHaveDefaultValues() {
    assertThat(fixture.getIsEnabled()).isFalse();
    assertThat(fixture.getIsBlocked()).isFalse();
  }

  @Test
  public void userShouldHaveIdGetterAndSetter() {
    User user = Mockito.mock(User.class, Mockito.CALLS_REAL_METHODS);
    Long id = new Random().nextLong();

    user.setId(id);

    assertThat(user).hasFieldOrPropertyWithValue("id", id);
    assertThat(user.getId()).isEqualTo(id);
  }

  @Test
  public void userShouldHaveUsernameGetterAndSetter() {
    String username = RandomStringUtils.random(11);

    fixture.setUsername(username);

    assertThat(fixture).hasFieldOrPropertyWithValue("username", username);
    assertThat(fixture.getUsername()).isEqualTo(username);
  }

  @Test
  public void userShouldHaveEmailGetterAndSetter() {
    String email = RandomStringUtils.random(11);

    fixture.setEmail(email);

    assertThat(fixture).hasFieldOrPropertyWithValue("email", email);
    assertThat(fixture.getEmail()).isEqualTo(email);
  }

  @Test
  public void userShouldHavePasswordGetterAndSetter() {
    String password = RandomStringUtils.random(11);

    fixture.setPassword(password);

    assertThat(fixture).hasFieldOrPropertyWithValue("password", password);
    assertThat(fixture.getPassword()).isEqualTo(password);
  }

  @Test
  public void userShouldHaveConfirmationGetterAndSetter() {
    String confirmation = RandomStringUtils.random(11);

    fixture.setConfirmation(confirmation);

    assertThat(fixture).hasFieldOrPropertyWithValue("confirmation", confirmation);
    assertThat(fixture.getConfirmation()).isEqualTo(confirmation);
  }

  @Test
  public void userShouldHaveEmailConfirmationTokenGetterAndSetter() {
    EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();

    fixture.setEmailConfirmationToken(emailConfirmationToken);

    assertThat(fixture).hasFieldOrPropertyWithValue("emailConfirmationToken",
        emailConfirmationToken);
    assertThat(fixture.getEmailConfirmationToken()).isEqualTo(emailConfirmationToken);
  }

  @Test
  public void userShouldHaveRoleGetterAndSetter() {
    Set<UserRoleAssociation> roles = new HashSet<>(
        Collections.singletonList(new UserRoleAssociation(fixture, new Role(TEST_USER_ROLE))));

    fixture.setRoles(roles);

    assertThat(fixture).hasFieldOrPropertyWithValue("roles", roles);
    assertThat(fixture.getRoles()).isEqualTo(roles);
  }

  @Test
  public void onCreateShouldHashPasswordWithBCrypt() {
    String password = RandomStringUtils.random(11);
    ReflectionTestUtils.setField(fixture, "password", password);
    doReturn(password).when(fixture).getPassword();
    doCallRealMethod().when(fixture).setPassword(ArgumentMatchers.anyString());

    fixture.onCreate();

    assertThat(new BCryptPasswordEncoder().matches(password,
        (String) ReflectionTestUtils.getField(fixture, "password"))).isTrue();
  }
}
