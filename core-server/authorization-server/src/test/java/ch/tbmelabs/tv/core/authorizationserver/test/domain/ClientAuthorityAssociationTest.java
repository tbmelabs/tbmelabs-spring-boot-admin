package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociationId;

public class ClientAuthorityAssociationTest {
  @Mock
  private Client clientFixture;

  @Mock
  private Authority authorityFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(clientFixture.getId()).thenReturn(new Random().nextLong());
    when(authorityFixture.getId()).thenReturn(new Random().nextLong());
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientAuthorityAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    assertThat(new ClientAuthorityAssociation(clientFixture, authorityFixture))
        .hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("clientAuthorityId", authorityFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture)
        .hasFieldOrPropertyWithValue("clientAuthority", authorityFixture);
  }

  @Test
  public void clientSetterShouldSaveId() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture);
  }

  @Test
  public void clientAuthoritySetterShouldSaveId() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation();
    fixture.setClientAuthority(authorityFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientAuthorityId", authorityFixture.getId())
        .hasFieldOrPropertyWithValue("clientAuthority", authorityFixture);
  }

  @Test
  public void gettersShouldReturnCorrectEntities() {
    ClientAuthorityAssociation fixture = new ClientAuthorityAssociation(clientFixture, authorityFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
    assertThat(fixture.getClientAuthority()).isEqualTo(authorityFixture);
  }
}