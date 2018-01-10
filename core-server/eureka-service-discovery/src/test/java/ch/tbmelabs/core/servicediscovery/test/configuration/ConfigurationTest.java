package ch.tbmelabs.core.servicediscovery.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class ConfigurationTest extends AbstractEurekaApplicationContextAware {
  private static final Integer EXPECTED_CONFIGURATION_COUNT = 2;

  @Test
  public void allConfigurationsShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".configuration")
        .getTypesAnnotatedWith(Configuration.class)).hasSize(EXPECTED_CONFIGURATION_COUNT).withFailMessage(
            "This package should only contain configuration classes annotated with %s!", Configuration.class);
  }
}