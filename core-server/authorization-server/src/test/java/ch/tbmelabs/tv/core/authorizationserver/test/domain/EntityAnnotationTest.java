package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import javax.persistence.Entity;
import org.junit.Test;
import org.reflections.Reflections;

public class EntityAnnotationTest {

  private static final Integer EXPECTED_ENTITIES_COUNT = 13;

  @Test
  public void allEntitiesShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain")
        .getTypesAnnotatedWith(Entity.class)).hasSize(EXPECTED_ENTITIES_COUNT);
  }

  @Test
  public void allEntitiesShouldExtendTheConfiguredSuperType() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain")
        .getSubTypesOf(AbstractAuditingEntity.class)).hasSize(EXPECTED_ENTITIES_COUNT);
  }
}
