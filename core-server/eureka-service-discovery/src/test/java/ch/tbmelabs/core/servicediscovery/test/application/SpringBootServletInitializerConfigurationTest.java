package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicatoinContextAwareJunitTest;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class SpringBootServletInitializerConfigurationTest extends AbstractEurekaApplicatoinContextAwareJunitTest {
  private static final String APPLICATION_CLASS_FIELD_NAME = "APPLICATION_SOURCE_CLASS";

  @Test
  public void applicationSourceShouldBePassedToSpringApplication()
      throws InstantiationException, IllegalAccessException {
    assertThat(Application.class).hasDeclaredFields(APPLICATION_CLASS_FIELD_NAME)
        .withFailMessage("Name this field self-explainable!");

    assertThat(Application.class.newInstance())
        .hasFieldOrPropertyWithValue(APPLICATION_CLASS_FIELD_NAME, Application.class)
        .withFailMessage("Pass the correct zuul source to the spring application!");
  }
}