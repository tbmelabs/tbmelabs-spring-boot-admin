package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import javax.persistence.IdClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociationId;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ClientScopeAssociationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static Client testClient = new Client();
  private static Scope testClientScope = new Scope();

  private ClientScopeAssociation testAssociation;

  @BeforeClass
  public static void beforeClassSetUp() {
    testClient.setId(new Random().nextLong());
    testClientScope.setId(new Random().nextLong());
  }

  @Before
  public void beforeTestSetUp() {
    testAssociation = new ClientScopeAssociation();
  }

  @Test
  public void classShouldBeAnnotatedWithComposedIdClass() {
    assertThat(ClientScopeAssociation.class).hasAnnotation(IdClass.class);
    assertThat(ClientScopeAssociation.class.getDeclaredAnnotation(IdClass.class).value()).isNotNull()
        .isEqualTo(ClientScopeAssociationId.class);
  }

  @Test
  public void constructorShouldSaveIdsAndEntities() {
    testAssociation = new ClientScopeAssociation(testClient, testClientScope);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("clientScopeId", testClientScope.getId())
        .hasFieldOrPropertyWithValue("client", testClient).hasFieldOrPropertyWithValue("clientScope", testClientScope);
  }

  @Test
  public void clientSetterShouldSaveId() {
    testAssociation.setClient(testClient);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientId", testClient.getId())
        .hasFieldOrPropertyWithValue("client", testClient);
  }

  @Test
  public void clientScopeSetterShouldSaveId() {
    testAssociation.setClientScope(testClientScope);

    assertThat(testAssociation).hasFieldOrPropertyWithValue("clientScopeId", testClientScope.getId())
        .hasFieldOrPropertyWithValue("clientScope", testClientScope);
  }
}