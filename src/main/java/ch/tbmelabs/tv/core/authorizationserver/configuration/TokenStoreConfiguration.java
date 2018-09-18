package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileConstants;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenStoreConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenStoreConfiguration.class);

  @Configuration
  @Profile({"!" + SpringApplicationProfileConstants.NO_REDIS})
  public class RedisTokenStoreConfiguration {

    private RedisConnectionFactory redisConnectionFactory;

    public RedisTokenStoreConfiguration(RedisConnectionFactory redisConnectionFactory) {
      this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisTokenStore tokenStore() {
      return new RedisTokenStore(redisConnectionFactory);
    }
  }

  @Configuration
  @Profile({SpringApplicationProfileConstants.NO_REDIS})
  public class JdbcTokenStoreConfiguration {

    public static final String JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME = "jdbcTokenStoreDatasource";

    private ApplicationContext applicationContext;

    public JdbcTokenStoreConfiguration(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
    }

    @Bean
    public JdbcTokenStore tokenStore() {
      LOGGER.warn("Profile \'{}\' is active: tokenstore will be of {}",
        SpringApplicationProfileEnum.NO_REDIS.getName(), JdbcTokenStore.class);

      return new JdbcTokenStore(
        (DataSource) applicationContext.getBean(JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME));
    }
  }
}
