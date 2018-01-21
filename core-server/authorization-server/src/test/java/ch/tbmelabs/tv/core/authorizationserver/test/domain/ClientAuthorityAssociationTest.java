package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociationId;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ClientAuthorityAssociationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static Client testClient = new Client();
  private static Authority testClientAuthority = new Authority();

  private ClientAuthorityAssociation testAssociation;

  @BeforeClass
  public static void beforeClassSetUp() {
    testClient.setId(new Random().nextLong());
    testClientAuthority.setId(new Random().nextLong());
  }

  @Before
  public void beforeTestSetUp() {
    testAssociation = new ClientAuthorityAssociation();
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientAuthorityAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientAuthorityAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientAuthorityAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    testAssociation = new ClientAuthorityAssociation(testClient, testClientAuthority);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("clientAuthorityId", testClientAuthority.getId())
        .hasFieldOrPropertyWithValue("client", testClient)
        .hasFieldOrPropertyWithValue("clientAuthority", testClientAuthority);
  }

  @Test
  public void clientSetterShouldSaveId() {
    testAssociation.setClient(testClient);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("client", testClient);
  }

  @Test
  public void clientAuthoritySetterShouldSaveId() {
    testAssociation.setClientAuthority(testClientAuthority);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientAuthorityId", testClientAuthority.getId())
        .hasFieldOrPropertyWithValue("clientAuthority", testClientAuthority);
  }
}