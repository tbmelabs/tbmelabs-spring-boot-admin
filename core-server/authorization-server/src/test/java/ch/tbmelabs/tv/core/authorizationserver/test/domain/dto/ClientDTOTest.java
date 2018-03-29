package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;

public class ClientDTOTest {
  @Spy
  private ClientDTO fixture;

  private final Client testClient = createTestClient();

  public static Client createTestClient() {
    Client client = new Client();
    client.setCreated(new Date());
    client.setLastUpdated(new Date());
    client.setId(new Random().nextLong());
    client.setClientId(RandomStringUtils.randomAlphabetic(36));
    client.setSecret(RandomStringUtils.randomAlphabetic(36));
    client.setAccessTokenValiditySeconds(new Random().nextInt());
    client.setRefreshTokenValiditySeconds(new Random().nextInt());
    client.setRedirectUri("https://tbme.tv");

    GrantType testGrantType = new GrantType("TEST_GRANT_TYPE");
    testGrantType.setId(new Random().nextLong());
    client.setGrantTypes(Arrays.asList(client.grantTypeToAssociation(testGrantType)));

    Authority testAuthority = new Authority("TEST_AUTHORITY");
    testAuthority.setId(new Random().nextLong());
    client.setGrantedAuthorities(Arrays.asList(client.authorityToAssociation(testAuthority)));

    Scope testScope = new Scope("TEST_SCOPE");
    testScope.setId(new Random().nextLong());
    client.setScopes(Arrays.asList(client.scopeToAssociation(testScope)));

    return client;
  }

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();
  }

  @Test
  public void clientDTOShouldHaveNoArgsConstructor() {
    assertThat(new ClientDTO()).isNotNull();
  }

  @Test
  public void clientDTOShouldHaveAllArgsConstructor() {
    List<GrantType> grantTypes = testClient.getGrantTypes().stream().map(ClientGrantTypeAssociation::getClientGrantType)
        .collect(Collectors.toList());
    List<Authority> grantedAuthorities = testClient.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getClientAuthority).collect(Collectors.toList());
    List<Scope> scopes = testClient.getScopes().stream().map(ClientScopeAssociation::getClientScope)
        .collect(Collectors.toList());

    assertThat(new ClientDTO(testClient, grantTypes, grantedAuthorities, scopes))
        .hasFieldOrPropertyWithValue("created", testClient.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testClient.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testClient.getId())
        .hasFieldOrPropertyWithValue("clientId", testClient.getClientId())
        .hasFieldOrPropertyWithValue("isSecretRequired", testClient.getIsSecretRequired())
        .hasFieldOrPropertyWithValue("isAutoApprove", testClient.getIsAutoApprove())
        .hasFieldOrPropertyWithValue("accessTokenValiditySeconds", testClient.getAccessTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("refreshTokenValiditySeconds", testClient.getRefreshTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("redirectUri", testClient.getRedirectUri())
        .hasFieldOrPropertyWithValue("grantTypes", grantTypes)
        .hasFieldOrPropertyWithValue("grantedAuthorities", grantedAuthorities)
        .hasFieldOrPropertyWithValue("scopes", scopes);

    assertThat(new ClientDTO(testClient, grantTypes, grantedAuthorities, scopes).getSecret()).isNull();
  }

  @Test
  public void clientDTOShouldHaveIdGetterAndSetter() {
    Client client = Mockito.mock(Client.class, Mockito.CALLS_REAL_METHODS);
    Long id = new Random().nextLong();

    client.setId(id);

    assertThat(client).hasFieldOrPropertyWithValue("id", id);
    assertThat(client.getId()).isEqualTo(id);
  }

  @Test
  public void clientDTOShouldHaveClientIdGetterAndSetter() {
    String clientId = UUID.randomUUID().toString();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientDTOShouldHaveSecretGetterAndSetter() {
    String secret = UUID.randomUUID().toString();

    fixture.setSecret(secret);

    assertThat(fixture).hasFieldOrPropertyWithValue("secret", secret);
    assertThat(fixture.getSecret()).isEqualTo(secret);
  }

  @Test
  public void clientDTOShouldHaveIsSecretRequiredGetterAndSetter() {
    boolean isSecretRequired = false;

    fixture.setIsSecretRequired(isSecretRequired);

    assertThat(fixture).hasFieldOrPropertyWithValue("isSecretRequired", false);
    assertThat(fixture.getIsSecretRequired()).isFalse();
  }

  @Test
  public void clientDTOShouldHaveIsAutoApproveGetterAndSetter() {
    boolean isAutoApprove = true;

    fixture.setIsAutoApprove(isAutoApprove);

    assertThat(fixture).hasFieldOrPropertyWithValue("isAutoApprove", true);
    assertThat(fixture.getIsAutoApprove()).isTrue();
  }

  @Test
  public void clientDTOShouldHaveAccessTokenValiditySecondsGetterAndSetter() {
    Integer accessTokenValiditySeconds = new Random().nextInt();

    fixture.setAccessTokenValiditySeconds(accessTokenValiditySeconds);

    assertThat(fixture).hasFieldOrPropertyWithValue("accessTokenValiditySeconds", accessTokenValiditySeconds);
    assertThat(fixture.getAccessTokenValiditySeconds()).isEqualTo(accessTokenValiditySeconds);
  }

  @Test
  public void clientDTOShouldHaveRefreshTokenValiditySecondsGetterAndSetter() {
    Integer refreshTokenValiditySeconds = new Random().nextInt();

    fixture.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);

    assertThat(fixture).hasFieldOrPropertyWithValue("refreshTokenValiditySeconds", refreshTokenValiditySeconds);
    assertThat(fixture.getRefreshTokenValiditySeconds()).isEqualTo(refreshTokenValiditySeconds);
  }

  @Test
  public void clientDTOShouldHaveRedirectUriGetterAndSetter() {
    String redirectUri = RandomStringUtils.random(11);

    fixture.setRedirectUri(redirectUri);

    assertThat(fixture).hasFieldOrPropertyWithValue("redirectUri", redirectUri);
    assertThat(fixture.getRedirectUri()).isEqualTo(redirectUri);
  }

  @Test
  public void clientDTOShouldHaveGrantTypesGetterAndSetter() {
    List<GrantType> grantTypes = testClient.getGrantTypes().stream().map(ClientGrantTypeAssociation::getClientGrantType)
        .collect(Collectors.toList());

    fixture.setGrantTypes(grantTypes);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantTypes", grantTypes);
    assertThat(fixture.getGrantTypes()).isEqualTo(grantTypes);
  }

  @Test
  public void clientDTOShouldHaveGrantedAuthoritiesGetterAndSetter() {
    List<Authority> grantedAuthorities = testClient.getGrantedAuthorities().stream()
        .map(ClientAuthorityAssociation::getClientAuthority).collect(Collectors.toList());

    fixture.setGrantedAuthorities(grantedAuthorities);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantedAuthorities", grantedAuthorities);
    assertThat(fixture.getGrantedAuthorities()).isEqualTo(grantedAuthorities);
  }

  @Test
  public void clientDTOShouldHaveScopesGetterAndSetter() {
    List<Scope> scopes = testClient.getScopes().stream().map(ClientScopeAssociation::getClientScope)
        .collect(Collectors.toList());

    fixture.setScopes(scopes);

    assertThat(fixture).hasFieldOrPropertyWithValue("scopes", scopes);
    assertThat(fixture.getScopes()).isEqualTo(scopes);
  }
}