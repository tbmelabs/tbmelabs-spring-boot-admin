package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenStoreConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class TokenStoreConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String REDIS_TOKEN_STORE_NAME = "redisTokenStore";
  private static final String JDBC_TOKEN_STORE_NAME = "jdbcTokenStore";

  @Test
  public void tokenStoreShouldBeAnnotated() {
    assertThat(TokenStoreConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void redisTokenStoreShouldOccurInProductiveEnvironmentOnly() throws NoSuchMethodException, SecurityException {
    Method redisTokenStoreConfiguration = TokenStoreConfiguration.class.getDeclaredMethod(REDIS_TOKEN_STORE_NAME,
        new Class[] { RedisConnectionFactory.class });

    assertThat(redisTokenStoreConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(redisTokenStoreConfiguration.getDeclaredAnnotation(Autowired.class)).isNotNull();
    assertThat(redisTokenStoreConfiguration.getDeclaredAnnotation(Profile.class)).isNull();
  }

  @Test
  public void jdbcTokenStoreShouldNotOccurInProductiveEnvironment() throws NoSuchMethodException, SecurityException {
    Method jdbcTokenStoreConfiguration = TokenStoreConfiguration.class.getDeclaredMethod(JDBC_TOKEN_STORE_NAME,
        new Class[] {});

    assertThat(jdbcTokenStoreConfiguration.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(jdbcTokenStoreConfiguration.getDeclaredAnnotation(Primary.class)).isNotNull();
    assertThat(jdbcTokenStoreConfiguration.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.NO_REDIS);
  }
}