package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final Integer EXPECTED_CONFIGURATION_COUNT = 9;

  @Test
  public void allConfigurationsShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".configuration")
        .getTypesAnnotatedWith(Configuration.class)).hasSize(EXPECTED_CONFIGURATION_COUNT);
  }
}