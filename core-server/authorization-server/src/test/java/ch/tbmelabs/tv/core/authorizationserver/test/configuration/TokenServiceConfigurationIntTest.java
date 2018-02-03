package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenServiceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class TokenServiceConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private TokenServiceConfiguration configuration;

  @Autowired
  private TokenService bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredTokenService() {
    assertThat(configuration.tokenServiceBean()).isEqualTo(bean);
  }
}