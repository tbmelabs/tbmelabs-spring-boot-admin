package ch.tbmelabs.tv.core.authorizationserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;

@Configuration
public class TokenStoreConfig {
  private static final String JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME = "jdbcTokenStoreDatasource";

  @Bean
  @Autowired
  @Profile("!dev")
  public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
    return new RedisTokenStore(redisConnectionFactory);
  }

  @Bean
  @Profile("dev")
  public JdbcTokenStore jdbcTokenStore() {
    DataSource jdbcTokenStoreDatasource = (DataSource) ApplicationContextHolder.getApplicationContext()
        .getBean(JDBC_TOKENSTORE_DATASOURCE_BEAN_NAME);

    return new JdbcTokenStore(jdbcTokenStoreDatasource);
  }
}