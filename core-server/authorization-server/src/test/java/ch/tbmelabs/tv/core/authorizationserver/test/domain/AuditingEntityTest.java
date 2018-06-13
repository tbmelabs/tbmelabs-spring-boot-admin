package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuditingEntity;
import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

public class AuditingEntityTest {

  @Spy
  private AuditingEntity fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldBeAnnotated() {
    assertThat(AuditingEntity.class).hasAnnotation(MappedSuperclass.class);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldExtendSerializable() {
    assertThat(Serializable.class).isAssignableFrom(AuditingEntity.class);
  }
}
