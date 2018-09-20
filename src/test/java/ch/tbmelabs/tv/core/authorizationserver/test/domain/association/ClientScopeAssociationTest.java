package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociationId;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClientScopeAssociationTest {

  @Mock
  private Client clientFixture;

  @Mock
  private Scope scopeFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(clientFixture).getId();
    doReturn(new Random().nextLong()).when(scopeFixture).getId();
  }

  @Test
  public void clientScopeAssociationShouldBeAnnotated() {
    assertThat(ClientScopeAssociation.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(ClientScopeAssociation.class.getDeclaredAnnotation(Table.class).name()).isNotNull()
      .isEqualTo("client_has_scopes");
  }

  @Test
  public void clientScopeAssociationShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientScopeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientScopeAssociation.class.getDeclaredAnnotation(IdClass.class).value())
      .isNotNull().isEqualTo(ClientScopeAssociationId.class);
  }

  @Test
  public void clientScopeAssociationShouldHaveNoArgsConstructor() {
    assertThat(new ClientScopeAssociation()).isNotNull();
  }

  @Test
  public void clientScopeAssociationShouldHaveAllArgsConstructor() {
    assertThat(new ClientScopeAssociation(clientFixture, scopeFixture))
      .hasFieldOrPropertyWithValue("client", clientFixture)
      .hasFieldOrPropertyWithValue("scope", scopeFixture);
  }

  @Test
  public void clientScopeAssociationShouldHaveClientGetterAndSetter() {
    ClientScopeAssociation fixture = new ClientScopeAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
  }

  @Test
  public void clientScopeAssociationShouldHaveScopeGetterAndSetter() {
    ClientScopeAssociation fixture = new ClientScopeAssociation();
    fixture.setScope(scopeFixture);

    assertThat(fixture.getScope()).isEqualTo(scopeFixture);
  }
}
