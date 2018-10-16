package ch.tbmelabs.configurationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.configurationserver.configuration.SecurityConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

public class SecurityConfigurationTest {

  SecurityConfiguration fixture;

  @Before
  public void beforeTestSetup() {
    fixture = new SecurityConfiguration();
  }

  @Test
  public void shouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class)
      .hasAnnotation(EnableWebSecurity.class).hasAnnotation(EnableGlobalMethodSecurity.class);
  }

  @Test
  public void enablesGlobalPrePostMethodSecurity() {
    final EnableGlobalMethodSecurity enableGlobalMethodSecurityAnnotation =
      SecurityConfiguration.class.getDeclaredAnnotation(EnableGlobalMethodSecurity.class);

    assertThat(enableGlobalMethodSecurityAnnotation.prePostEnabled()).isTrue();
  }

  @Test
  public void shouldHavePublicConstructor() {
    assertThat(new SecurityConfiguration()).isNotNull();
  }
}
