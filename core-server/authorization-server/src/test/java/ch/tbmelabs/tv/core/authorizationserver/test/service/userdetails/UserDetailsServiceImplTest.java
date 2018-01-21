package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testdata.TestUserManager;

public class UserDetailsServiceImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private TestUserManager testUserManager;

  @Test
  public void userDetailsServiceShouldLoadCorrectUserDetailsForUsername()
      throws IllegalAccessException, NoSuchFieldException, SecurityException {
    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(testUserManager.getUserUser().getUsername());

    Field userField = UserDetailsImpl.class.getDeclaredField("user");
    userField.setAccessible(true);
    assertThat(((User) userField.get(userDetails)).getId()).isNotNull()
        .isEqualTo(testUserManager.getUserUser().getId());
  }
}