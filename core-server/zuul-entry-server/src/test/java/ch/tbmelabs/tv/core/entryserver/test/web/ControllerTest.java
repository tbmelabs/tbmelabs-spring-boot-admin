package ch.tbmelabs.tv.core.entryserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class ControllerTest extends AbstractZuulApplicationContextAware {
  private static final Integer EXPECTED_CONTROLLER_COUNT = 0;

  private static Set<Class<?>> controllers = new HashSet<>();

  @BeforeClass
  public static void beforeClassSetUp() {
    controllers.addAll(
        new Reflections(Application.class.getPackage().getName() + ".web").getTypesAnnotatedWith(Controller.class));
    controllers.addAll(
        new Reflections(Application.class.getPackage().getName() + ".web").getTypesAnnotatedWith(RestController.class));
  }

  @Test
  public void allControllersShouldBeAnnotated() {
    assertThat(ControllerTest.controllers).hasSize(EXPECTED_CONTROLLER_COUNT);
  }

  @Test
  public void allMappingsShouldSpecifyARequestMethod() {
    controllers
        .forEach(controller -> assertThat(Arrays.stream(controller.getDeclaredMethods())
            .filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null
                && method.getDeclaredAnnotation(RequestMapping.class).method() == null)
            .findAny().isPresent()).isFalse());
  }
}