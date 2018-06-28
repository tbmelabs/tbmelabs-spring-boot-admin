package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.configuration.FlywayConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class FlywayConfigurationTest {

  @Test
  public void springMailConfigurationShouldBeAnnotated() {
    assertThat(FlywayConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(PropertySource.class);

    assertThat(FlywayConfiguration.class.getDeclaredAnnotation(PropertySource.class).value())
        .hasSize(1).containsExactly("classpath:configuration/flyway.properties");
  }

  @Test
  public void springMailConfigurationShouldHavePublicConstructor() {
    assertThat(new FlywayConfiguration()).isNotNull();
  }
}
