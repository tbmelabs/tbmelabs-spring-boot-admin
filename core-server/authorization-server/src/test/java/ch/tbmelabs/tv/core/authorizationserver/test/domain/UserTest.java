package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;

public class UserTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static User testUser = new User();
  private static Role testRole = new Role();

  @Autowired
  private TestUserManager testUserManager;

  @BeforeClass
  public static void beforeClassSetUp() {
    testRole.setName("TEST");
  }

  @Test
  public void newUserShouldHaveDefaultValues() {
    assertThat(testUser.getIsEnabled()).isTrue();
    assertThat(testUser.getIsBlocked()).isFalse();
  }

  @Test
  public void userShouldConvertRoleToUserRoleAssociation() {
    List<UserRoleAssociation> newAssociation = (List<UserRoleAssociation>) testUserManager.getUserUser()
        .rolesToAssociations(Arrays.asList(testRole));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getUser().getId()).isNotNull().isEqualTo(testUserManager.getUserUser().getId());
    assertThat(newAssociation.get(0).getUserRole().getName()).isNotNull().isEqualTo(testRole.getName());
  }
}