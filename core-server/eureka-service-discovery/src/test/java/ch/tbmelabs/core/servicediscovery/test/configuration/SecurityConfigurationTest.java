package ch.tbmelabs.core.servicediscovery.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.servicediscovery.configuration.SecurityConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

public class SecurityConfigurationTest {

  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableWebSecurity.class).hasAnnotation(EnableGlobalMethodSecurity.class);
  }

  @Test
  public void securityConfigurationShouldHavePublicConstructor() {
    assertThat(new SecurityConfiguration()).isNotNull();
  }
}
