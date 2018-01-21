package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestRoleManager;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class UserDetailsImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ROLE_PREFIX = "ROLE_";

  private User testUser;
  private UserDetailsImpl testUserDetails;

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
  public void userDetailsImplShouldReturnInformationEqualToUser() {
    assertThat(testUserDetails.getUsername()).isNotNull().isEqualTo(testUser.getUsername());
    assertThat(testUserDetails.getPassword()).isNotNull().isEqualTo(testUser.getPassword());
    assertThat(testUserDetails.isEnabled()).isNotNull().isEqualTo(testUser.getIsEnabled());
    assertThat(testUserDetails.isAccountNonLocked()).isNotNull().isEqualTo(!testUser.getIsBlocked());
    assertThat(testUserDetails.isAccountNonExpired()).isNotNull().isTrue();
    assertThat(testUserDetails.isCredentialsNonExpired()).isNotNull().isTrue();
    assertThat(
        testUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .isNotNull().isNotEmpty()
            .containsExactly(ROLE_PREFIX + SecurityRole.USER, ROLE_PREFIX + testRoleManager.getTestRole().getName());
  }
}