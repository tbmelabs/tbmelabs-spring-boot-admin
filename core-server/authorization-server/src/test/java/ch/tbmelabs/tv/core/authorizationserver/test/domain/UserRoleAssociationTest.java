package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociationId;

public class UserRoleAssociationTest {
  @Mock
  private User userFixture;

  @Mock
  private Role roleFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(userFixture.getId()).thenReturn(new Random().nextLong());
    when(roleFixture.getId()).thenReturn(new Random().nextLong());
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(UserRoleAssociation.class).hasAnnotation(IdClass.class);
    assertThat(UserRoleAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(UserRoleAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    assertThat(new UserRoleAssociation(userFixture, roleFixture))
        .hasFieldOrPropertyWithValue("userId", userFixture.getId())
        .hasFieldOrPropertyWithValue("userRoleId", roleFixture.getId()).hasFieldOrPropertyWithValue("user", userFixture)
        .hasFieldOrPropertyWithValue("userRole", roleFixture);
  }

  @Test
  public void userSetterShouldSaveId() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setUser(userFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("userId", userFixture.getId()).hasFieldOrPropertyWithValue("user",
        userFixture);
  }

  @Test
  public void userRoleSetterShouldSaveId() {
    UserRoleAssociation fixture = new UserRoleAssociation();
    fixture.setUserRole(roleFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("userRoleId", roleFixture.getId())
        .hasFieldOrPropertyWithValue("userRole", roleFixture);
  }

  @Test
  public void gettersShouldReturnCorrectEntities() {
    UserRoleAssociation fixture = new UserRoleAssociation(userFixture, roleFixture);

    assertThat(fixture.getUser()).isEqualTo(userFixture);
    assertThat(fixture.getUserRole()).isEqualTo(roleFixture);
  }
}