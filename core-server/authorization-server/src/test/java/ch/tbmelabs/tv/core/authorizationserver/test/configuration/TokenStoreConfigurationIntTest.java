package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenStoreConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class TokenStoreConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Mock
  private RedisConnectionFactory redisConnectionFactory;

  @Autowired
  private TokenStoreConfiguration configuration;

  @Autowired
  @Qualifier("tokenStore")
  private TokenStore bean;

  @Autowired
  @Qualifier("jdbcTokenStore")
  private TokenStore secondaryBean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredDataSource() {
    assertThat(configuration.tokenStore(redisConnectionFactory)).isEqualTo(bean);
  }

  @Test
  public void secondaryBeanShouldBeInitialized() {
    assertThat(configuration.jdbcTokenStore()).isEqualTo(secondaryBean);
  }
}