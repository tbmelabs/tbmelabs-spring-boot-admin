package ch.tbmelabs.tv.core.authorizationserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import java.util.HashSet;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class ControllerAnnotationTest {

  private static final Integer EXPECTED_CONTROLLER_COUNT = 9;

  private static Set<Class<?>> allControllers = new HashSet<>();
  private static Set<Class<?>> annotatedControllers = new HashSet<>();

  @BeforeClass
  public static void beforeClassSetUp() {
    allControllers.addAll(new Reflections(Application.class.getPackage().getName() + ".web")
        .getSubTypesOf(Object.class));

    annotatedControllers.addAll(new Reflections(Application.class.getPackage().getName() + ".web")
        .getTypesAnnotatedWith(Controller.class));
    annotatedControllers.addAll(new Reflections(Application.class.getPackage().getName() + ".web")
        .getTypesAnnotatedWith(RestController.class));
  }

  @Test
  public void packageShouldOnlyContainControllers() {
    allControllers.forEach(
        controller -> assertThat(controller.getClass().getSimpleName()).contains("Controller"));
  }

  @Test
  public void allControllersShouldBeAnnotated() {
    assertThat(ControllerAnnotationTest.annotatedControllers).hasSize(EXPECTED_CONTROLLER_COUNT);
  }
}
