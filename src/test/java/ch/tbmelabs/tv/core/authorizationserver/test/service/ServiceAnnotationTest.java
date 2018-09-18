package ch.tbmelabs.tv.core.authorizationserver.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

public class ServiceAnnotationTest {

  private static final Integer EXPECTED_SERVICE_COUNT = 13;

  @Test
  public void packageShouldOnlyContainServices() {
    new Reflections(Application.class.getPackage().getName() + ".service")
        .getSubTypesOf(Object.class)
        .forEach(service -> assertThat(service.getClass().getSimpleName()).contains("Service"));
  }

  @Test
  public void allServicesShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".service")
        .getTypesAnnotatedWith(Service.class)).hasSize(EXPECTED_SERVICE_COUNT);
  }
}
