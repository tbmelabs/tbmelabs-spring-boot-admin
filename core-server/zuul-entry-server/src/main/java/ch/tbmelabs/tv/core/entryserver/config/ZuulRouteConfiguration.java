package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class ZuulRouteConfiguration {
  @Profile("dev")
  @PropertySource({ "classpath:zuul-dev.properties" })
  public class ZuulDevConfiguration {

  }

  @Profile("prod")
  @PropertySource({ "classpath:zuul.properties", "classpath:zuul-dev.properties" })
  public class ZuulProdConfiguration {

  }
}