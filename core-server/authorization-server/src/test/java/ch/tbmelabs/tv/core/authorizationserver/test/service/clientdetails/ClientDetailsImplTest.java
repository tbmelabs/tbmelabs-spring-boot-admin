package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ClientDetailsImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String CLIENT_REDIRECT_URIS = "uirone" + ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR
      + "uritwo";

  private Client testClient;
  private ClientDetailsImpl testClientDetails;

  @Before
  public void beforeTestSetUp() {
    testClient = new Client();
    testClient.setClientId(UUID.randomUUID().toString());
    testClient.setSecret(UUID.randomUUID().toString());
    testClient.setAccessTokenValiditySeconds(3600);
    testClient.setRefreshTokenValiditySeconds(7200);
    testClient.setRedirectUri(CLIENT_REDIRECT_URIS);
    testClient.setGrantTypes(testClient.grantTypesToAssociations(Arrays.asList(new GrantType("TEST"))));
    testClient.setGrantedAuthorities(testClient.authoritiesToAssociations(Arrays.asList(new Authority("TEST"))));
    testClient.setScopes(testClient.scopesToAssociations(Arrays.asList(new Scope("TEST"))));

    testClientDetails = new ClientDetailsImpl(testClient);
  }

  @Test
  public void clientDetailsImplShouldReturnInformationEquelToUser() {
    assertThat(testClientDetails.getClientId()).isNotNull().isEqualTo(testClient.getClientId());
    assertThat(testClientDetails.getResourceIds()).isNotNull().isEqualTo(new HashSet<>());
    assertThat(testClientDetails.isSecretRequired()).isNotNull().isEqualTo(testClient.getIsSecretRequired());
    assertThat(testClientDetails.isAutoApprove(new String())).isNotNull().isEqualTo(testClient.getIsAutoApprove());
    assertThat(testClient.getSecret()).isNotNull().isEqualTo(testClient.getSecret());
    assertThat(testClient.getAccessTokenValiditySeconds()).isNotNull()
        .isEqualTo(testClient.getAccessTokenValiditySeconds());
    assertThat(testClient.getRefreshTokenValiditySeconds()).isNotNull()
        .isEqualTo(testClient.getRefreshTokenValiditySeconds());
    assertThat(testClientDetails.isScoped()).isNotNull().isEqualTo(!testClient.getScopes().isEmpty());
    assertThat(testClientDetails.getRegisteredRedirectUri()).isNotNull().isEqualTo(
        new HashSet<>(Arrays.asList(CLIENT_REDIRECT_URIS.split(ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR))));
    assertThat(testClientDetails.getAuthorities()).isNotNull().isEqualTo(testClient.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getClientAuthority).collect(Collectors.toList()));
    assertThat(testClientDetails.getAdditionalInformation()).isNotNull().isEqualTo(new HashMap<>());
  }
}