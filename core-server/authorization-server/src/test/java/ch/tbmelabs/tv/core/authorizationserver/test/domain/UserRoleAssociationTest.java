package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociationId;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class UserRoleAssociationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static User testUser = new User();
  private static Role testRole = new Role();

  private UserRoleAssociation testAssociation;

  @BeforeClass
  public static void beforeClassSetUp() {
    testUser.setId(new Random().nextLong());
    testRole.setId(new Random().nextLong());
  }

  @Before
  public void beforeTestSetUp() {
    testAssociation = new UserRoleAssociation();
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(UserRoleAssociation.class).hasAnnotation(IdClass.class);
    assertThat(UserRoleAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(UserRoleAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    testAssociation = new UserRoleAssociation(testUser, testRole);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("userId", testUser.getId())
        .hasFieldOrPropertyWithValue("userRoleId", testRole.getId()).hasFieldOrPropertyWithValue("user", testUser)
        .hasFieldOrPropertyWithValue("userRole", testRole);
  }

  @Test
  public void userSetterShouldSaveId() {
    testAssociation.setUser(testUser);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("userId", testUser.getId())
        .hasFieldOrPropertyWithValue("user", testUser);
  }

  @Test
  public void userRoleSetterShouldSaveId() {
    testAssociation.setUserRole(testRole);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("userRoleId", testRole.getId())
        .hasFieldOrPropertyWithValue("userRole", testRole);
  }
}