package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import ch.tbmelabs.tv.core.authorizationserver.configuration.AuthenticationManagerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;

public class AuthenticationManagerConfigurationIntTest
    extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private AuthenticationManagerConfiguration configuration;

  @Autowired
  private AuthenticationManager bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredAuthenticationManager() throws Exception {
    assertThat(configuration.authenticationManager()).isEqualTo(bean);
  }
}
