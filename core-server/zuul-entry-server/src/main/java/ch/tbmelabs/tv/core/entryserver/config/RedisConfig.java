package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class RedisConfig {
  @Bean
  @Autowired
  public RedisTokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
    return new RedisTokenStore(redisConnectionFactory);
  }
}