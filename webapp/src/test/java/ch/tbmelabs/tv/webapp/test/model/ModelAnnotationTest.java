package ch.tbmelabs.tv.webapp.test.model;

import static org.junit.Assert.assertEquals;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import ch.tbmelabs.tv.webapp.test.JunitSpringImpl;
import lombok.NoArgsConstructor;

public class ModelAnnotationTest implements JunitSpringImpl {
  private static final int EXPECTED_MODEL_COUNT = 5;

  @Test
  public void isJavaXPersistenceEntityAnnotated() {
    assertEquals(
        "Please annotate all model classes with " + Entity.class + " to make them scannable by " + ComponentScan.class,
        EXPECTED_MODEL_COUNT,
        new Reflections("ch.tbmelabs.tv.webapp.model").getTypesAnnotatedWith(Entity.class).size());
  }

  @Test
  public void isJavaXPersistenceTableAnnotated() {
    assertEquals("Please annotate all model classes with " + Table.class + " to specify a table name for each entity!",
        false, new Reflections("ch.tbmelabs.tv.webapp.model").getTypesAnnotatedWith(Entity.class).stream()
            .filter(clazz -> clazz.getDeclaredAnnotation(Table.class).name().isEmpty() == true).findAny().isPresent());
  }

  @Test
  public void isLombokNoArgsConstructorAnnotated() {
    assertEquals(
        "Please annotate all model classes with " + NoArgsConstructor.class + " to provide a default-constructor to "
            + SpringBootApplication.class,
        false,
        new Reflections("ch.tbmelabs.tv.webapp.model").getTypesAnnotatedWith(Entity.class).stream().filter(clazz -> {
          try {
            return clazz.getDeclaredConstructor(new Class<?>[0]) == null;
          } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException(e);
          }
        }).findAny().isPresent());
  }
}