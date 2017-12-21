package ch.tbmelabs.tv.core.authorizationserver.configuration;

import javax.sql.DataSource;

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
  private static final String JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME = "jdbcTokenStoreDatasource";

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  @Autowired
  public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
    return new RedisTokenStore(redisConnectionFactory);
  }

  @Bean
  @Primary
  @Profile({ SpringApplicationProfile.DEV, SpringApplicationProfile.TEST })
  public JdbcTokenStore jdbcTokenStore() {
    DataSource jdbcTokenStoreDatasource = (DataSource) applicationContext.getBean(JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME);

    return new JdbcTokenStore(jdbcTokenStoreDatasource);
  }
}