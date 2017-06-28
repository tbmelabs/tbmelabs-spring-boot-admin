package ch.tbmelabs.tv.webapp.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.reflections.Reflections;

import ch.tbmelabs.tv.webapp.model.NicelyDocumentedEntity;
import ch.tbmelabs.tv.webapp.test.JunitSpringImpl;

public class ModelExtentionsTest implements JunitSpringImpl {
  private static final int EXPECTED_MODEL_COUNT = 5;

  @Test
  public void isModelNicelyDocumented() {
    assertEquals(
        "Please extend your model classes from " + NicelyDocumentedEntity.class
            + " to provide a good-quality data documentation!",
        EXPECTED_MODEL_COUNT,
        new Reflections("ch.tbmelabs.tv.webapp.model").getSubTypesOf(NicelyDocumentedEntity.class).size());
  }
}