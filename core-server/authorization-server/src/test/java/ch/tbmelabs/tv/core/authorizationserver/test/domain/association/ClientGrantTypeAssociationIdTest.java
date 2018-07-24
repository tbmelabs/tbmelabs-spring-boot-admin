package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientGrantTypeAssociationIdTest {

  @Spy
  private ClientGrantTypeAssociationId fixture;

  @Test
  public void clientGrantTypeAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientGrantTypeAssociationId()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveClientGetterAndSetter() {
    Client client = Mockito.mock(Client.class);

    fixture.setClient(client);

    assertThat(fixture).hasFieldOrPropertyWithValue("client", client);
    assertThat(fixture.getClient()).isEqualTo(client);
  }

  @Test
  public void clientGrantTypeAssociationIdShouldHaveGrantTypeGetterAndSetter() {
    GrantType grantType = Mockito.mock(GrantType.class);

    fixture.setGrantType(grantType);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantType", grantType);
    assertThat(fixture.getGrantType()).isEqualTo(grantType);
  }
}
