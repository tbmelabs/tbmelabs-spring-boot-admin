package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociationId;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleAssociationIdTest {

  @Spy
  private UserRoleAssociationId fixture;

  @Test
  public void userRoleAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new UserRoleAssociationId()).isNotNull();
  }

  @Test
  public void userRoleAssociationIdShouldHaveUserGetterAndSetter() {
    User user = Mockito.mock(User.class);

    fixture.setUser(user);

    assertThat(fixture).hasFieldOrPropertyWithValue("user", user);
    assertThat(fixture.getUser()).isEqualTo(user);
  }

  @Test
  public void userRoleAssociationIdShouldHaveUserRoleGetterAndSetter() {
    Role role = Mockito.mock(Role.class);

    fixture.setRole(role);

    assertThat(fixture).hasFieldOrPropertyWithValue("role", role);
    assertThat(fixture.getRole()).isEqualTo(role);
  }
}
