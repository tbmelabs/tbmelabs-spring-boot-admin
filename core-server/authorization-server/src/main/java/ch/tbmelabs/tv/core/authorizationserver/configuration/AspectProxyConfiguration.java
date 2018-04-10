package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import ch.tbmelabs.tv.core.authorizationserver.security.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectProxyConfiguration {
  @Autowired
  private Environment environment;

  @Bean
  public LoggingAspect loggingAspect() {
    return new LoggingAspect(environment);
  }
}