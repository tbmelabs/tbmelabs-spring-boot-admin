package ch.tbmelabs.tv.core.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import org.junit.Test;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.mock.env.MockEnvironment;

public class SpringBootServletInitializerConfigurationTest {

  private static final String APPLICATION_CLASS_FIELD_NAME = "APPLICATION_SOURCE_CLASS";

  @Test
  public void applicationExtendsSpringBootServletInitializer() {
    assertThat(SpringBootServletInitializer.class).isAssignableFrom(Application.class);
  }

  @Test
  public void applicationSourceShouldBePassedToSpringApplication() {
    assertThat(Application.class).hasDeclaredFields(APPLICATION_CLASS_FIELD_NAME);
    assertThat(new Application(new MockEnvironment()))
        .hasFieldOrPropertyWithValue(APPLICATION_CLASS_FIELD_NAME, Application.class);
  }
}
