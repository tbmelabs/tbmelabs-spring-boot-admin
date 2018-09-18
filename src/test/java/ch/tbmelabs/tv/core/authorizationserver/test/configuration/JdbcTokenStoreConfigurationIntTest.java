package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;
import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenStoreConfiguration.JdbcTokenStoreConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({SpringApplicationProfileConstants.TEST,
  SpringApplicationProfileConstants.NO_REDIS})
public class JdbcTokenStoreConfigurationIntTest
  extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Autowired
  private JdbcTokenStoreConfiguration jdbcConfiguration;

  @Autowired
  private TokenStore bean;

  @Test
  public void secondaryBeanShouldBeInitialized() {
    assertThat(jdbcConfiguration.tokenStore()).isEqualTo(bean);
  }
}
