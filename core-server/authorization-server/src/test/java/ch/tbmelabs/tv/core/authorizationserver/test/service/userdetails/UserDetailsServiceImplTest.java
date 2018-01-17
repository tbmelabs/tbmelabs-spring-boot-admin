package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestRoleManager;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class UserDetailsServiceImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private TestRoleManager testRoleManager;

  @Autowired
  private TestUserManager testUserManager;

  @Test
  @SuppressWarnings("unchecked")
  public void userDetailsServiceShouldLoadCorrectUserDetailsForUsername() {
    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(testUserManager.getUserUser().getUsername());

    assertThat(userDetails).isNotNull();
    assertThat(userDetails.getUsername()).isNotNull().isEqualTo(testUserManager.getUserUser().getUsername());
    assertThat(userDetails.getPassword()).isNotNull().isEqualTo(testUserManager.getUserUser().getPassword());
    assertThat((ArrayList<Role>) userDetails.getAuthorities()).isNotNull()
        .containsExactly(roleRepository.findByName(SecurityRole.USER), testRoleManager.getTestRole());
  }
}