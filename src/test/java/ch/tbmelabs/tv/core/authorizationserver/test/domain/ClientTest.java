package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.AbstractAuditingEntity;
import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class ClientTest {

  private static final String TEST_CLIENT_GRANT_TYPE = RandomStringUtils.random(11);
  private static final String TEST_CLIENT_AUTHORITY = RandomStringUtils.random(11);
  private static final String TEST_CLIENT_SCOPE = RandomStringUtils.random(11);

  @Spy
  private Client fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();
  }

  @Test
  public void clientShouldBeAnnotated() {
    assertThat(Client.class).hasAnnotation(Entity.class).hasAnnotation(Table.class);

    assertThat(Client.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("clients");
  }

  @Test
  public void clientShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(AbstractAuditingEntity.class).isAssignableFrom(Client.class);
  }

  @Test
  public void clientShouldHaveNoArgsConstructor() {
    assertThat(new Client()).isNotNull();
  }

  @Test
  public void newClientShouldHaveDefaultValues() {
    assertThat(fixture.getIsSecretRequired()).isTrue();
    assertThat(fixture.getIsAutoApprove()).isFalse();
  }

  @Test
  public void clientShouldHaveIdGetterAndSetter() {
    Client client = Mockito.mock(Client.class, Mockito.CALLS_REAL_METHODS);
    Long id = new Random().nextLong();

    client.setId(id);

    assertThat(client).hasFieldOrPropertyWithValue("id", id);
    assertThat(client.getId()).isEqualTo(id);
  }

  @Test
  public void clientShouldHaveClientIdGetterAndSetter() {
    String clientId = UUID.randomUUID().toString();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientShouldHaveSecretGetterAndSetter() {
    String secret = UUID.randomUUID().toString();

    fixture.setSecret(secret);

    assertThat(fixture).hasFieldOrPropertyWithValue("secret", secret);
    assertThat(fixture.getSecret()).isEqualTo(secret);
  }

  @Test
  public void clientShouldHaveIsSecretRequiredGetterAndSetter() {
    boolean isSecretRequired = false;

    fixture.setIsSecretRequired(isSecretRequired);

    assertThat(fixture).hasFieldOrPropertyWithValue("isSecretRequired", false);
    assertThat(fixture.getIsSecretRequired()).isFalse();
  }

  @Test
  public void clientShouldHaveIsAutoApproveGetterAndSetter() {
    boolean isAutoApprove = true;

    fixture.setIsAutoApprove(isAutoApprove);

    assertThat(fixture).hasFieldOrPropertyWithValue("isAutoApprove", true);
    assertThat(fixture.getIsAutoApprove()).isTrue();
  }

  @Test
  public void clientShouldHaveAccessTokenValiditySecondsGetterAndSetter() {
    Integer accessTokenValiditySeconds = new Random().nextInt();

    fixture.setAccessTokenValiditySeconds(accessTokenValiditySeconds);

    assertThat(fixture).hasFieldOrPropertyWithValue("accessTokenValiditySeconds",
      accessTokenValiditySeconds);
    assertThat(fixture.getAccessTokenValiditySeconds()).isEqualTo(accessTokenValiditySeconds);
  }

  @Test
  public void clientShouldHaveRefreshTokenValiditySecondsGetterAndSetter() {
    Integer refreshTokenValiditySeconds = new Random().nextInt();

    fixture.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);

    assertThat(fixture).hasFieldOrPropertyWithValue("refreshTokenValiditySeconds",
      refreshTokenValiditySeconds);
    assertThat(fixture.getRefreshTokenValiditySeconds()).isEqualTo(refreshTokenValiditySeconds);
  }

  @Test
  public void clientShouldHaveRedirectUriGetterAndSetter() {
    String redirectUri = RandomStringUtils.random(11);

    fixture.setRedirectUri(redirectUri);

    assertThat(fixture).hasFieldOrPropertyWithValue("redirectUri", redirectUri);
    assertThat(fixture.getRedirectUri()).isEqualTo(redirectUri);
  }

  @Test
  public void clientShouldHaveGrantTypesGetterAndSetter() {
    Set<ClientGrantTypeAssociation> grantTypes = new HashSet<>(Collections.singletonList(
      new ClientGrantTypeAssociation(fixture, new GrantType(TEST_CLIENT_GRANT_TYPE))));

    fixture.setGrantTypes(grantTypes);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantTypes", grantTypes);
    assertThat(fixture.getGrantTypes()).isEqualTo(grantTypes);
  }

  @Test
  public void clientShouldHaveGrantedAuthoritiesGetterAndSetter() {
    Set<ClientAuthorityAssociation> grantedAuthorities = new HashSet<>(Collections.singletonList(
      new ClientAuthorityAssociation(fixture, new Authority(TEST_CLIENT_AUTHORITY))));

    fixture.setAuthorities(grantedAuthorities);

    assertThat(fixture).hasFieldOrPropertyWithValue("authorities", grantedAuthorities);
    assertThat(fixture.getAuthorities()).isEqualTo(grantedAuthorities);
  }

  @Test
  public void clientShouldHaveScopesGetterAndSetter() {
    Set<ClientScopeAssociation> scopes = new HashSet<>(Collections
      .singletonList(new ClientScopeAssociation(fixture, new Scope(TEST_CLIENT_SCOPE))));

    fixture.setScopes(scopes);

    assertThat(fixture).hasFieldOrPropertyWithValue("scopes", scopes);
    assertThat(fixture.getScopes()).isEqualTo(scopes);
  }
}
