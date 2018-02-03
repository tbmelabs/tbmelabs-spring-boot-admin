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
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociationId;

public class ClientGrantTypeAssociationTest {
  @Mock
  private Client clientFixture;

  @Mock
  private GrantType grantTypeFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(clientFixture.getId()).thenReturn(new Random().nextLong());
    when(grantTypeFixture.getId()).thenReturn(new Random().nextLong());
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientGrantTypeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientGrantTypeAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientGrantTypeAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    assertThat(new ClientGrantTypeAssociation(clientFixture, grantTypeFixture))
        .hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("clientGrantTypeId", grantTypeFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture)
        .hasFieldOrPropertyWithValue("clientGrantType", grantTypeFixture);
  }

  @Test
  public void clientSetterShouldSaveId() {
    ClientGrantTypeAssociation fixture = new ClientGrantTypeAssociation();
    fixture.setClient(clientFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientFixture.getId())
        .hasFieldOrPropertyWithValue("client", clientFixture);
  }

  @Test
  public void clientGrantTypeSetterShouldSaveId() {
    ClientGrantTypeAssociation fixture = new ClientGrantTypeAssociation();
    fixture.setGrantType(grantTypeFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientGrantTypeId", grantTypeFixture.getId())
        .hasFieldOrPropertyWithValue("clientGrantType", grantTypeFixture);
  }

  @Test
  public void gettersShouldReturnCorrectEntities() {
    ClientGrantTypeAssociation fixture = new ClientGrantTypeAssociation(clientFixture, grantTypeFixture);

    assertThat(fixture.getClient()).isEqualTo(clientFixture);
    assertThat(fixture.getClientGrantType()).isEqualTo(grantTypeFixture);
  }
}