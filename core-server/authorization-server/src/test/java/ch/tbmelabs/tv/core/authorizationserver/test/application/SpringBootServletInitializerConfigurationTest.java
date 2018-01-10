package ch.tbmelabs.tv.core.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class SpringBootServletInitializerConfigurationTest
    extends AbstractOAuth2AuthorizationApplicationContextAware {
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