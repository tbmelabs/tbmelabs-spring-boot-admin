package ch.tbmelabs.tv.webapp.test.model.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.tbmelabs.tv.webapp.test.JunitSpringImpl;

public class RepositoryAnnotationTest implements JunitSpringImpl {
  private static final int EXPECTED_REPOSITORY_COUNT = 5;

  @Test
  public void isOrgSpringframeworkDataRestCoreAnnotationRepositoryRestResourceAnnotated() {
    assertEquals(
        "Please annotate all repositories with " + RepositoryRestResource.class + " to make them scannable by "
            + ComponentScan.class,
        EXPECTED_REPOSITORY_COUNT, new Reflections("ch.tbmelabs.tv.webapp.model.repository")
            .getTypesAnnotatedWith(RepositoryRestResource.class).size());
  }
}