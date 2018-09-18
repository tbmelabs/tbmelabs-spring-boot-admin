package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenServiceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

public class TokenServiceConfigurationIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private TokenServiceConfiguration configuration;

  @Autowired
  @Qualifier("tokenServiceBean")
  private DefaultTokenServices bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredTokenService() {
    assertThat(configuration.tokenServiceBean()).isEqualTo(bean);
  }
}
