package ch.tbmelabs.springbootadmin.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import ch.tbmelabs.actuatorendpointssecurityutils.annotation.EnableActuatorEndpointSecurity;
import ch.tbmelabs.springbootadmin.configuration.ActuatorEndpointSecurityConfiguration;

public class ActuatorEndpointSecurityConfigurationTest {

  @Test
  public void shouldBeAnnotated() {
    assertThat(ActuatorEndpointSecurityConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableActuatorEndpointSecurity.class);
  }

  @Test
  public void shouldHavePublicConstructor() {
    assertThat(new ActuatorEndpointSecurityConfiguration()).isNotNull();
  }
}
