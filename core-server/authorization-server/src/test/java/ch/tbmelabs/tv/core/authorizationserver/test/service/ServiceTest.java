package ch.tbmelabs.tv.core.authorizationserver.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class ServiceTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final Integer EXPECTED_SERVICE_COUNT = 5;

  @Test
  public void allServicesShouldBeAnnotated() {
    assertThat(
        new Reflections(Application.class.getPackage().getName() + ".service").getTypesAnnotatedWith(Service.class))
            .hasSize(EXPECTED_SERVICE_COUNT)
            .withFailMessage("This package should only contain service classes annotated with %s!", Service.class);
  }
}