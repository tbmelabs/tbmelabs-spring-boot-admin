package ch.tbmelabs.tv.core.authorizationserver.configuration;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
public class TokenStoreConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(TokenStoreConfiguration.class);

  private static final String JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME = "jdbcTokenStoreDatasource";

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  @Autowired
  public RedisTokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
    return new RedisTokenStore(redisConnectionFactory);
  }

  @Bean
  @Primary
  @Profile({ SpringApplicationProfile.NO_REDIS })
  public JdbcTokenStore jdbcTokenStore() {
    LOGGER.warn("Profile \"" + SpringApplicationProfile.NO_REDIS + "\" is active: tokenstore will be of type "
        + JdbcTokenStore.class);

    return new JdbcTokenStore((DataSource) applicationContext.getBean(JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME));
  }
}