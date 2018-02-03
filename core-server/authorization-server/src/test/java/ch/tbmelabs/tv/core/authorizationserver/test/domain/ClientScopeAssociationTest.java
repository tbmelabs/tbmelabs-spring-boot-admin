package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociationId;

public class ClientScopeAssociationTest {
  @Mock
  private Client clientFixture;

  @Mock
  private Scope scopeFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(clientFixture.getId()).thenReturn(new Random().nextLong());
    when(scopeFixture.getId()).thenReturn(new Random().nextLong());
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientScopeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientScopeAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientScopeAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    assertThat(new ClientScopeAssociation(clientFixture, scopeFixture))
        .hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("clientScopeId", scopeFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture).hasFieldOrPropertyWithValue("clientScope", scopeFixture);
  }

  @Test
  public void clientSetterShouldSaveId() {
    ClientScopeAssociation fixture = new ClientScopeAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture);
  }

  @Test
  public void clientScopeSetterShouldSaveId() {
    ClientScopeAssociation fixture = new ClientScopeAssociation();
    fixture.setClientScope(scopeFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientScopeId", scopeFixture.getId())
        .hasFieldOrPropertyWithValue("clientScope", scopeFixture);
  }

  @Test
  public void gettersShouldReturnCorrectEntities() {
    ClientScopeAssociation fixture = new ClientScopeAssociation(clientFixture, scopeFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
    assertThat(fixture.getClientScope()).isEqualTo(scopeFixture);
  }
}