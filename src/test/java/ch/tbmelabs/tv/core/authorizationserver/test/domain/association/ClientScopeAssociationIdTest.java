package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientScopeAssociationIdTest {

  @Spy
  private ClientScopeAssociationId fixture;

  @Test
  public void clientScopeAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientScopeAssociationId()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveClientGetterAndSetter() {
    Client client = Mockito.mock(Client.class);

    fixture.setClient(client);

    assertThat(fixture).hasFieldOrPropertyWithValue("client", client);
    assertThat(fixture.getClient()).isEqualTo(client);
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveAuthorityIdGetterAndSetter() {
    Scope scope = Mockito.mock(Scope.class);

    fixture.setScope(scope);

    assertThat(fixture).hasFieldOrPropertyWithValue("scope", scope);
    assertThat(fixture.getScope()).isEqualTo(scope);
  }
}
