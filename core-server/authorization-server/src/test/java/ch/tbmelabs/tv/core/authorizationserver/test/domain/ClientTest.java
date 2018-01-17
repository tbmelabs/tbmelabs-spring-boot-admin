package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ClientTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String TEST_GRANT_TYPE = "TEST";
  private static final String TEST_CLIENT_AUTHORITY = "TEST";

  private static Client testClient = new Client();

  @BeforeClass
  public static void beforeClassSetUp() {
    testClient.setClientId(UUID.randomUUID().toString());
  }

  @Test
  public void newClientShouldHaveDefaultValues() {
    assertThat(testClient.isSecretRequired()).isTrue();
    assertThat(testClient.getIsAutoApprove()).isFalse();
  }

  @Test
  public void clientShouldConvertGrantTypeToClientGrantTypeAssociation() {
    List<ClientGrantTypeAssociation> newAssociation = (List<ClientGrantTypeAssociation>) testClient
        .grantTypesToAssociations(Arrays.asList(new GrantType(TEST_GRANT_TYPE)));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getClient().getClientId()).isNotNull().isEqualTo(testClient.getClientId());
    assertThat(newAssociation.get(0).getClientGrantType().getName()).isNotNull().isEqualTo(TEST_GRANT_TYPE);
  }

  @Test
  public void clientShouldConvertScopeToClientScopeAssociation() {
    List<ClientAuthorityAssociation> newAssociation = (List<ClientAuthorityAssociation>) testClient
        .authoritiesToAssociations(Arrays.asList(new Authority(TEST_CLIENT_AUTHORITY)));

    assertThat(newAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(newAssociation.get(0).getClient().getClientId()).isNotNull().isEqualTo(testClient.getClientId());
    assertThat(newAssociation.get(0).getClientAuthority().getName()).isNotNull().isEqualTo(TEST_CLIENT_AUTHORITY);
  }
}