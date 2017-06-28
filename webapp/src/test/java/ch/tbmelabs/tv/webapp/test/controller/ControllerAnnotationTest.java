package ch.tbmelabs.tv.webapp.test.controller;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.tbmelabs.tv.webapp.test.JunitSpringImpl;

public class ControllerAnnotationTest implements JunitSpringImpl {
  private static final int EXPECTED_CONTROLLER_COUNT = 5;

  @Test
  public void isOrgSpringframeworkStereotypeControllerAnnotated() {
    assertEquals(
        "Please annotate all controller classes with " + Controller.class + " to make them scannable by "
            + ComponentScan.class,
        EXPECTED_CONTROLLER_COUNT,
        new Reflections("ch.tbmelabs.tv.webapp").getTypesAnnotatedWith(Controller.class).size());
  }

  @Test
  public void isRequestMethodAnnotated() {
    List<Method> undeclaredRequestMethods = new ArrayList<>();

    new Reflections("ch.tbmelabs.tv.webapp").getTypesAnnotatedWith(Controller.class).stream()
        .forEach(clazz -> Arrays.asList(clazz.getDeclaredMethods()).stream()
            .filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null)
            .filter(annotatedMethod -> Arrays
                .asList(annotatedMethod.getDeclaredAnnotation(RequestMapping.class).method()).isEmpty())
            .forEach(undeclaredRequestMethods::add));

    assertEquals("Please specify a " + RequestMethod.class + " for all your " + RequestMapping.class, true,
        undeclaredRequestMethods.isEmpty());
  }
}