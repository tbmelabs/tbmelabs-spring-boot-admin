package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class RepositoryTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final Integer EXPECTED_REPOSITORY_COUNT = 5;

  @Test
  public void allRepositoriesShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain.repository")
        .getTypesAnnotatedWith(Repository.class)).hasSize(EXPECTED_REPOSITORY_COUNT).withFailMessage(
            "This package should only contain repository classes annotated with %s!", Repository.class);
  }

  @Test
  public void allRepositoriesShouldExtendsTheCRUDRepositoryInterface() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain.repository")
        .getSubTypesOf(CrudRepository.class)).hasSize(EXPECTED_REPOSITORY_COUNT).withFailMessage(
            "This package should only contain repository classes extending from %s!", CrudRepository.class);
  }
}