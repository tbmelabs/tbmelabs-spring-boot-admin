package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;

public class UserTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String TEST_ROLE = "TEST";

  @Autowired
  private TestUserManager testUserManager;

  @Test
  public void newUserShouldHaveDefaultValues() {
    User newUser = new User();

    assertThat(newUser.getIsEnabled()).isTrue();
    assertThat(newUser.getIsBlocked()).isFalse();
  }

  @Test
  public void userShouldConvertRoleToUserRoleAssociation() {
    List<UserRoleAssociation> newAssociation = (List<UserRoleAssociation>) testUserManager.getUserUser()
        .rolesToAssociations(Arrays.asList(new Role(TEST_ROLE)));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getUser().getId()).isNotNull().isEqualTo(testUserManager.getUserUser().getId());
    assertThat(newAssociation.get(0).getUserRole().getName()).isNotNull().isEqualTo(TEST_ROLE);
  }
}