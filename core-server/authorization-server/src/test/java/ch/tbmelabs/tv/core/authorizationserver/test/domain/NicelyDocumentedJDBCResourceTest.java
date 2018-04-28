package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

public class NicelyDocumentedJDBCResourceTest {

  @Spy
  private NicelyDocumentedJDBCResource fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldBeAnnotated() {
    assertThat(NicelyDocumentedJDBCResource.class).hasAnnotation(MappedSuperclass.class);
  }

  @Test
  public void nicelyDocumentedJDBCResourceShouldExtendSerializable() {
    assertThat(Serializable.class).isAssignableFrom(NicelyDocumentedJDBCResource.class);
  }

  @Test
  public void onCreateShouldSetCreateAndLastUpdatedToCurrentTime() {
    fixture.onCreate();

    assertThat(fixture.created).isNotNull();
    assertThat(fixture.lastUpdated).isNotNull();
  }

  @Test
  public void onUpdateShouldSetLastUpdatedToCurrentTime() {
    Date created = new Date();
    fixture.created = created;

    fixture.onUpdate();

    assertThat(fixture.created).isEqualTo(created);
    assertThat(fixture.lastUpdated).isNotNull();
  }
}
