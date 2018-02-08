package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;

public class ClientDetailsImplTest {
  private static final String CLIENT_REDIRECT_URIS = "uirone" + ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR
      + "uritwo";

  @Mock
  private Client clientFixture;

  private static ClientDetailsImpl clientDetailsImpl;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(clientFixture).grantTypesToAssociations(Mockito.anyList());
    doCallRealMethod().when(clientFixture).authoritiesToAssociations(Mockito.anyList());
    doCallRealMethod().when(clientFixture).scopesToAssociations(Mockito.anyList());

    doReturn(UUID.randomUUID().toString()).when(clientFixture).getClientId();
    doReturn(UUID.randomUUID().toString()).when(clientFixture).getSecret();
    doReturn(3600).when(clientFixture).getAccessTokenValiditySeconds();
    doReturn(7200).when(clientFixture).getRefreshTokenValiditySeconds();
    doReturn(CLIENT_REDIRECT_URIS).when(clientFixture).getRedirectUri();

    clientDetailsImpl = new ClientDetailsImpl(clientFixture);
  }

  @Test
  public void clientDetailsImplShouldReturnInformationEquelToUser() {
    assertThat(clientDetailsImpl.getClientId()).isNotNull().isEqualTo(clientFixture.getClientId());
    assertThat(clientDetailsImpl.getResourceIds()).isNotNull().isEqualTo(new HashSet<>());
    assertThat(clientDetailsImpl.isSecretRequired()).isNotNull().isEqualTo(clientFixture.getIsSecretRequired());
    assertThat(clientDetailsImpl.isAutoApprove(new String())).isNotNull().isEqualTo(clientFixture.getIsAutoApprove());
    assertThat(clientDetailsImpl.getClientSecret()).isNotNull().isEqualTo(clientFixture.getSecret());
    assertThat(clientDetailsImpl.getAccessTokenValiditySeconds()).isNotNull()
        .isEqualTo(clientDetailsImpl.getAccessTokenValiditySeconds());
    assertThat(clientDetailsImpl.getRefreshTokenValiditySeconds()).isNotNull()
        .isEqualTo(clientDetailsImpl.getRefreshTokenValiditySeconds());
    assertThat(clientDetailsImpl.isScoped()).isNotNull().isEqualTo(!clientFixture.getScopes().isEmpty());
    assertThat(clientDetailsImpl.getRegisteredRedirectUri()).isNotNull().isEqualTo(
        new HashSet<>(Arrays.asList(CLIENT_REDIRECT_URIS.split(ClientDetailsImpl.CLIENT_REDIRECT_URI_SPLITTERATOR))));
    assertThat(clientDetailsImpl.getAuthorities()).isNotNull().isEqualTo(clientFixture.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getClientAuthority).collect(Collectors.toList()));
    assertThat(clientDetailsImpl.getAdditionalInformation()).isNotNull().isEqualTo(new HashMap<>());
  }
}