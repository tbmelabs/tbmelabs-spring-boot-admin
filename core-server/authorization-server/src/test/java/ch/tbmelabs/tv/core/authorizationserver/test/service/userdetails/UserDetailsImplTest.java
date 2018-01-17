package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestRoleManager;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class UserDetailsImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private User testUser;
  private UserDetailsImpl testUserDetails;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private TestRoleManager testRoleManager;

  @Autowired
  private TestUserManager testUserManager;

  @Before
  public void beforeTestSetUp() {
    testUser = testUserManager.getUserUser();
    testUserDetails = new UserDetailsImpl(testUser);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void userDetailsImplsShouldReturnInformationEqualToUser() {
    assertThat(testUserDetails.getUsername()).isNotNull().isEqualTo(testUser.getUsername());
    assertThat(testUserDetails.getPassword()).isNotNull().isEqualTo(testUser.getPassword());
    assertThat((ArrayList<Role>) testUserDetails.getAuthorities()).isNotNull()
        .containsExactly(roleRepository.findByName(SecurityRole.USER), testRoleManager.getTestRole());
  }
}