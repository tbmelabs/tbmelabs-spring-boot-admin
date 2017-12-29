package ch.tbmelabs.tv.core.entryserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

// @Configuration
public class RequestContextListenerConfiguration {
  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }
}