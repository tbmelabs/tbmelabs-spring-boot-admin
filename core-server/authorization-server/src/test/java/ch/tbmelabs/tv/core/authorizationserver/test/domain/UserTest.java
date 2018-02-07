package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;

public class UserTest {
  private static final String TEST_USER_ROLE = "TEST";

  @Mock
  private User fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();

    doCallRealMethod().when(fixture).onCreate();
    doCallRealMethod().when(fixture).rolesToAssociations(Mockito.anyList());
  }

  @Test
  public void userShouldBeAnnotated() {
    assertThat(User.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(User.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("users");
    assertThat(User.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(User.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
  }

  @Test
  public void userShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(User.class);
  }

  @Test
  public void userShouldHaveNoArgsConstructor() {
    assertThat(new User()).isNotNull();
  }

  @Test
  public void newUserShouldHaveDefaultValues() {
    User fixture = new User();

    assertThat(fixture.getIsEnabled()).isTrue();
    assertThat(fixture.getIsBlocked()).isFalse();
  }

  @Test
  public void userShouldHaveIdGetterAndSetter() {
    User fixture = new User();
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void userShouldHaveUsernameGetterAndSetter() {
    User fixture = new User();
    String username = RandomStringUtils.randomAlphabetic(11);

    fixture.setUsername(username);

    assertThat(fixture).hasFieldOrPropertyWithValue("username", username);
    assertThat(fixture.getUsername()).isEqualTo(username);
  }

  @Test
  public void userShouldHaveEmailGetterAndSetter() {
    User fixture = new User();
    String email = RandomStringUtils.randomAlphabetic(11);

    fixture.setEmail(email);

    assertThat(fixture).hasFieldOrPropertyWithValue("email", email);
    assertThat(fixture.getEmail()).isEqualTo(email);
  }

  @Test
  public void userShouldHavePasswordGetterAndSetter() {
    User fixture = new User();
    String password = RandomStringUtils.randomAlphabetic(11);

    fixture.setPassword(password);

    assertThat(fixture).hasFieldOrPropertyWithValue("password", password);
    assertThat(fixture.getPassword()).isEqualTo(password);
  }

  @Test
  public void userShouldHaveConfirmationGetterAndSetter() {
    User fixture = new User();
    String confirmation = RandomStringUtils.randomAlphabetic(11);

    fixture.setConfirmation(confirmation);

    assertThat(fixture).hasFieldOrPropertyWithValue("confirmation", confirmation);
    assertThat(fixture.getConfirmation()).isEqualTo(confirmation);
  }

  @Test
  public void userShouldHaveGrantedAuthoritiesGetterAndSetter() {
    User fixture = new User();
    Collection<UserRoleAssociation> grantedAuthorities = Arrays
        .asList(new UserRoleAssociation(fixture, new Role(TEST_USER_ROLE)));

    fixture.setGrantedAuthorities(grantedAuthorities);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantedAuthorities", grantedAuthorities);
    assertThat(fixture.getGrantedAuthorities()).isEqualTo(grantedAuthorities);
  }

  @Test
  public void onCreateShouldHashPasswordWithBCrypt() {
    String password = RandomStringUtils.randomAlphabetic(11);
    ReflectionTestUtils.setField(fixture, "password", password);
    doReturn(password).when(fixture).getPassword();
    doCallRealMethod().when(fixture).setPassword(Mockito.anyString());

    fixture.onCreate();

    assertThat(
        new BCryptPasswordEncoder().matches(password, (String) ReflectionTestUtils.getField(fixture, "password")))
            .isTrue();
  }

  @Test
  public void userShouldConvertRoleToUserRoleAssociation() {
    List<UserRoleAssociation> newAssociation = (List<UserRoleAssociation>) fixture
        .rolesToAssociations(Arrays.asList(new Role(TEST_USER_ROLE)));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getUser()).isNotNull().isEqualTo(fixture);
    assertThat(newAssociation.get(0).getUserRole().getName()).isNotNull().isEqualTo(TEST_USER_ROLE);
  }
}