package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.authorizationserver.configuration.CorsFilterConfiguration;

public class CorsFilterConfigurationTest {
  @Test
  public void corsFilterConfigurationShouldBeAnnotated() {
    assertThat(CorsFilterConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void logoutCorsFilterBeanShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    assertThat(CorsFilterConfiguration.class.getDeclaredMethod("logoutCorsFilter", new Class<?>[] {})
        .getDeclaredAnnotation(Bean.class)).isNotNull();
  }
}