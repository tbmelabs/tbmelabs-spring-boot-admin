package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class SpringBootServletInitializerConfigurationTest extends AbstractEurekaApplicationContextAware {
  private static final String APPLICATION_CLASS_FIELD_NAME = "APPLICATION_SOURCE_CLASS";

  @Test
  public void applicationSourceShouldBePassedToSpringApplication()
      throws InstantiationException, IllegalAccessException {
    assertThat(Application.class).hasDeclaredFields(APPLICATION_CLASS_FIELD_NAME);
    assertThat(Application.class.newInstance()).hasFieldOrPropertyWithValue(APPLICATION_CLASS_FIELD_NAME,
        Application.class);
  }
}