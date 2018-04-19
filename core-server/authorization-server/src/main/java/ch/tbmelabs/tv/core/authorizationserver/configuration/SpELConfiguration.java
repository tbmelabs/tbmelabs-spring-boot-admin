package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import ch.tbmelabs.tv.core.authorizationserver.security.spel.SecurityEvaluationContextExtension;

@Configuration
public class SpELConfiguration {
  @Bean
  public EvaluationContextExtension securityExtension() {
    return new SecurityEvaluationContextExtension();
  }
}
