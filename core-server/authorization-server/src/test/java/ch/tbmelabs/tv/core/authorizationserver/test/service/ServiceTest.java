package ch.tbmelabs.tv.core.authorizationserver.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ServiceTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final Integer EXPECTED_SERVICE_COUNT = 5;

  @Test
  public void allServicesShouldBeAnnotated() {
    assertThat(
        new Reflections(Application.class.getPackage().getName() + ".service").getTypesAnnotatedWith(Service.class))
            .hasSize(EXPECTED_SERVICE_COUNT);
  }
}