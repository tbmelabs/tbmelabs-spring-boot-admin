package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientAuthorityAssociationIdTest {

  @Spy
  private ClientAuthorityAssociationId fixture;

  @Test
  public void clientAuthorityAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientAuthorityAssociationId()).isNotNull();
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveClientGetterAndSetter() {
    Client client = Mockito.mock(Client.class);

    fixture.setClient(client);

    assertThat(fixture).hasFieldOrPropertyWithValue("client", client);
    assertThat(fixture.getClient()).isEqualTo(client);
  }

  @Test
  public void clientAuthorityAssociationIdShouldHaveAuthorityGetterAndSetter() {
    Authority authority = Mockito.mock(Authority.class);

    fixture.setAuthority(authority);

    assertThat(fixture).hasFieldOrPropertyWithValue("authority", authority);
    assertThat(fixture.getAuthority()).isEqualTo(authority);
  }
}
