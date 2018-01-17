package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestRoleManager;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class UserDetailsServiceImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String ROLE_PREFIX = "ROLE_";

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private TestRoleManager testRoleManager;

  @Autowired
  private TestUserManager testUserManager;

  @Test
  public void userDetailsServiceShouldLoadCorrectUserDetailsForUsername() {
    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(testUserManager.getUserUser().getUsername());

    assertThat(userDetails).isNotNull();
    assertThat(userDetails.getUsername()).isNotNull().isEqualTo(testUserManager.getUserUser().getUsername());
    assertThat(userDetails.getPassword()).isNotNull().isEqualTo(testUserManager.getUserUser().getPassword());
    assertThat(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .isNotNull().isNotEmpty()
        .containsExactly(ROLE_PREFIX + SecurityRole.USER, ROLE_PREFIX + testRoleManager.getTestRole().getName());

  }
}