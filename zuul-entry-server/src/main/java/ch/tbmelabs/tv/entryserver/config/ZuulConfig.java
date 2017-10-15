package ch.tbmelabs.tv.entryserver.config;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableZuulProxy
public class ZuulConfig {
  @Configuration
  @Profile("dev")
  @PropertySource({ "classpath:zuul-dev.properties" })
  public class ZuulDevConfig {
  }

  @Configuration
  @Profile("!dev")
  @PropertySource({ "classpath:zuul.properties", "classpath:zuul-dev.properties" })
  public class ZuulPropertyLoader {
  }
}