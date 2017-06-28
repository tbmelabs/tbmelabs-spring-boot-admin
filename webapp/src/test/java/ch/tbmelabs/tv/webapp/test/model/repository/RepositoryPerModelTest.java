package ch.tbmelabs.tv.webapp.test.model.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.data.repository.CrudRepository;

import ch.tbmelabs.tv.webapp.model.NicelyDocumentedEntity;
import ch.tbmelabs.tv.webapp.test.JunitSpringImpl;

public class RepositoryPerModelTest implements JunitSpringImpl {
  @Test
  public void hasEachModelARepository() {
    assertEquals("Please construct a " + CrudRepository.class + " for each of your model classes!",
        new Reflections("ch.tbmelabs.tv.webapp.model").getSubTypesOf(NicelyDocumentedEntity.class).size(),
        new Reflections("ch.tbmelabs.tv.webapp.model.repository").getSubTypesOf(CrudRepository.class).size());
  }
}