package ch.tbmelabs.tv.core.authorizationserver.test.domain.association;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociationId;
import java.util.Random;
import org.junit.Test;

public class ClientGrantTypeAssociationIdTest {

  @Test
  public void clientGrantTypeAssociationIdShouldHaveNoArgsConstructor() {
    assertThat(new ClientGrantTypeAssociationId()).isNotNull();
  }

  @Test
  public void clientGrantTypeAssociationIdShouldHaveClientIdGetterAndSetter() {
    ClientGrantTypeAssociationId fixture = new ClientGrantTypeAssociationId();
    Long clientId = new Random().nextLong();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientGrantTypeAssociationIdShouldHaveGrantTypeIdGetterAndSetter() {
    ClientGrantTypeAssociationId fixture = new ClientGrantTypeAssociationId();
    Long clientGrantTypeId = new Random().nextLong();

    fixture.setClientGrantTypeId(clientGrantTypeId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientGrantTypeId", clientGrantTypeId);
    assertThat(fixture.getClientGrantTypeId()).isEqualTo(clientGrantTypeId);
  }
}
