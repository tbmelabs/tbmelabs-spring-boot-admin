package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociationId;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClientAuthorityAssociationTest {

  @Mock
  private Client clientFixture;

  @Mock
  private Authority authorityFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(clientFixture).getId();
    doReturn(new Random().nextLong()).when(authorityFixture).getId();
  }

  @Test
  public void clientAuthorityAssociationShouldBeAnnotated() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(Entity.class)
        .hasAnnotation(Table.class);

    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(Table.class).name())
        .isNotNull().isEqualTo("client_has_authorities");
  }

  @Test
  public void clientAuthorityAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(IdClass.class).value())
        .isNotNull().isEqualTo(ClientAuthorityAssociationId.class);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveNoArgsConstructor() {
    assertThat(new ClientAuthorityAssociation()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationShouldHaveAllArgsConstructor() {
    assertThat(new ClientAuthorityAssociation(clientFixture, authorityFixture))
        .hasFieldOrPropertyWithValue("client", clientFixture)
        .hasFieldOrPropertyWithValue("authority", authorityFixture);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveClientGetterAndSetter() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
  }

  @Test
  public void clientAuthorityAssociationShouldHaveAuthorityGetterAndSetter() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setAuthority(authorityFixture);

    assertThat(fixture.getAuthority()).isEqualTo(authorityFixture);
  }
}
