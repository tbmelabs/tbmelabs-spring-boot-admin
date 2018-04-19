package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import ch.tbmelabs.tv.core.authorizationserver.configuration.SpELConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.security.spel.SecurityEvaluationContextExtension;

public class SpELConfigurationTest {

  @Test
  public void spelConfigurationShouldBeAnnotated() {
    assertThat(SpELConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void spelConfigurationShouldHavePublicConstructor() {
    assertThat(new SpELConfiguration()).isNotNull();
  }

  @Test
  public void securityExtensionShouldReturnAnEvaluationContextExtension() {
    assertThat(new SpELConfiguration().securityExtension())
        .isInstanceOf(SecurityEvaluationContextExtension.class);
  }
}
