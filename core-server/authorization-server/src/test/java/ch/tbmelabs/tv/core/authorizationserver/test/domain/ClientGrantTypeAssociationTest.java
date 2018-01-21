package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociationId;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ClientGrantTypeAssociationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static Client testClient = new Client();
  private static GrantType testClientGrantType = new GrantType();

  private ClientGrantTypeAssociation testAssociation;

  @BeforeClass
  public static void beforeClassSetUp() {
    testClient.setId(new Random().nextLong());
    testClientGrantType.setId(new Random().nextLong());
  }

  @Before
  public void beforeTestSetUp() {
    testAssociation = new ClientGrantTypeAssociation();
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientGrantTypeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientGrantTypeAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientGrantTypeAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    testAssociation = new ClientGrantTypeAssociation(testClient, testClientGrantType);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("clientGrantTypeId", testClientGrantType.getId())
        .hasFieldOrPropertyWithValue("client", testClient)
        .hasFieldOrPropertyWithValue("clientGrantType", testClientGrantType);
  }

  @Test
  public void clientSetterShouldSaveId() {
    testAssociation.setClient(testClient);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("client", testClient);
  }

  @Test
  public void clientGrantTypeSetterShouldSaveId() {
    testAssociation.setGrantType(testClientGrantType);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientGrantTypeId", testClientGrantType.getId())
        .hasFieldOrPropertyWithValue("clientGrantType", testClientGrantType);
  }
}