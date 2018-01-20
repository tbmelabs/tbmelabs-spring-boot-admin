package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class ConfigurationTest extends AbstractZuulApplicationContextAware {
  private static final Integer EXPECTED_CONFIGURATION_COUNT = 2;

  @Test
  public void allConfigurationsShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".configuration")
        .getTypesAnnotatedWith(Configuration.class)).hasSize(EXPECTED_CONFIGURATION_COUNT);
  }
}