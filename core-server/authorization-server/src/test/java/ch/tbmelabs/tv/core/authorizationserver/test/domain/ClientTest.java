package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class ClientTest {

  private static final String TEST_CLIENT_GRANT_TYPE = "TEST";
  private static final String TEST_CLIENT_AUTHORITY = "TEST";
  private static final String TEST_CLIENT_SCOPE = "TEST";

  @Spy
  private Client fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();
  }

  @Test
  public void clientShouldBeAnnotated() {
    assertThat(Client.class).hasAnnotation(Entity.class).hasAnnotation(Table.class)
        .hasAnnotation(JsonInclude.class).hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Client.class.getDeclaredAnnotation(Table.class).name()).isEqualTo("clients");
    assertThat(Client.class.getDeclaredAnnotation(JsonInclude.class).value())
        .isEqualTo(Include.NON_NULL);
    assertThat(Client.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown())
        .isTrue();
  }

  @Test
  public void clientShouldExtendNicelyDocumentedJDBCResource() {
    assertThat(NicelyDocumentedJDBCResource.class).isAssignableFrom(Client.class);
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
    Collection<ClientGrantTypeAssociation> grantTypes = Collections.singletonList(
        new ClientGrantTypeAssociation(fixture, new GrantType(TEST_CLIENT_GRANT_TYPE)));

    fixture.setGrantTypes(grantTypes);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantTypes", grantTypes);
    assertThat(fixture.getGrantTypes()).isEqualTo(grantTypes);
  }

  @Test
  public void clientShouldHaveGrantedAuthoritiesGetterAndSetter() {
    Collection<ClientAuthorityAssociation> grantedAuthorities = Collections.singletonList(
        new ClientAuthorityAssociation(fixture, new Authority(TEST_CLIENT_AUTHORITY)));

    fixture.setGrantedAuthorities(grantedAuthorities);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantedAuthorities", grantedAuthorities);
    assertThat(fixture.getGrantedAuthorities()).isEqualTo(grantedAuthorities);
  }

  @Test
  public void clientShouldHaveScopesGetterAndSetter() {
    Collection<ClientScopeAssociation> scopes =
        Collections
            .singletonList(new ClientScopeAssociation(fixture, new Scope(TEST_CLIENT_SCOPE)));

    fixture.setScopes(scopes);

    assertThat(fixture).hasFieldOrPropertyWithValue("scopes", scopes);
    assertThat(fixture.getScopes()).isEqualTo(scopes);

  }
}
