package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;
import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;

public class ClientTest {
  private static final String TEST_CLIENT_GRANT_TYPE = "TEST";
  private static final String TEST_CLIENT_AUTHORITY = "TEST";
  private static final String TEST_CLIENT_SCOPE = "TEST";

  @Mock
  private Client fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new Random().nextLong()).when(fixture).getId();

    doCallRealMethod().when(fixture).grantTypesToAssociations(Mockito.anyList());
    doCallRealMethod().when(fixture).authoritiesToAssociations(Mockito.anyList());
    doCallRealMethod().when(fixture).scopesToAssociations(Mockito.anyList());
  }

  @Test
  public void clientShouldBeAnnotated() {
    assertThat(Client.class).hasAnnotation(Entity.class).hasAnnotation(Table.class).hasAnnotation(JsonInclude.class)
        .hasAnnotation(JsonIgnoreProperties.class);

    assertThat(Client.class.getDeclaredAnnotation(Table.class).name()).isNotNull().isEqualTo("clients");
    assertThat(Client.class.getDeclaredAnnotation(JsonInclude.class).value()).isNotNull().isEqualTo(Include.NON_NULL);
    assertThat(Client.class.getDeclaredAnnotation(JsonIgnoreProperties.class).ignoreUnknown()).isNotNull().isTrue();
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
    Client fixture = new Client();

    assertThat(fixture.getIsSecretRequired()).isTrue();
    assertThat(fixture.getIsAutoApprove()).isFalse();
  }

  @Test
  public void clientShouldHaveIdGetterAndSetter() {
    Client fixture = new Client();
    Long id = new Random().nextLong();

    fixture.setId(id);

    assertThat(fixture).hasFieldOrPropertyWithValue("id", id);
    assertThat(fixture.getId()).isEqualTo(id);
  }

  @Test
  public void clientShouldHaveClientIdGetterAndSetter() {
    Client fixture = new Client();
    String clientId = UUID.randomUUID().toString();

    fixture.setClientId(clientId);

    assertThat(fixture).hasFieldOrPropertyWithValue("clientId", clientId);
    assertThat(fixture.getClientId()).isEqualTo(clientId);
  }

  @Test
  public void clientShouldHaveSecretGetterAndSetter() {
    Client fixture = new Client();
    String secret = UUID.randomUUID().toString();

    fixture.setSecret(secret);

    assertThat(fixture).hasFieldOrPropertyWithValue("secret", secret);
    assertThat(fixture.getSecret()).isEqualTo(secret);
  }

  @Test
  public void clientShouldHaveIsSecretRequiredGetterAndSetter() {
    Client fixture = new Client();
    boolean isSecretRequired = false;

    fixture.setIsSecretRequired(isSecretRequired);

    assertThat(fixture).hasFieldOrPropertyWithValue("isSecretRequired", false);
    assertThat(fixture.getIsSecretRequired()).isFalse();
  }

  @Test
  public void clientShouldHaveIsAutoApproveGetterAndSetter() {
    Client fixture = new Client();
    boolean isAutoApprove = true;

    fixture.setIsAutoApprove(isAutoApprove);

    assertThat(fixture).hasFieldOrPropertyWithValue("isAutoApprove", true);
    assertThat(fixture.getIsAutoApprove()).isTrue();
  }

  @Test
  public void clientShouldHaveAccessTokenValiditySecondsGetterAndSetter() {
    Client fixture = new Client();
    Integer accessTokenValiditySeconds = new Random().nextInt();

    fixture.setAccessTokenValiditySeconds(accessTokenValiditySeconds);

    assertThat(fixture).hasFieldOrPropertyWithValue("accessTokenValiditySeconds", accessTokenValiditySeconds);
    assertThat(fixture.getAccessTokenValiditySeconds()).isEqualTo(accessTokenValiditySeconds);
  }

  @Test
  public void clientShouldHaveRedirectUriGetterAndSetter() {
    Client fixture = new Client();
    String redirectUri = RandomStringUtils.random(11);

    fixture.setRedirectUri(redirectUri);

    assertThat(fixture).hasFieldOrPropertyWithValue("redirectUri", redirectUri);
    assertThat(fixture.getRedirectUri()).isEqualTo(redirectUri);
  }

  @Test
  public void clientShouldHaveGrantTypesGetterAndSetter() {
    Client fixture = new Client();
    fixture.setId(new Random().nextLong());
    Collection<ClientGrantTypeAssociation> grantTypes = Arrays
        .asList(new ClientGrantTypeAssociation(fixture, new GrantType(TEST_CLIENT_GRANT_TYPE)));

    fixture.setGrantTypes(grantTypes);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantTypes", grantTypes);
    assertThat(fixture.getGrantTypes()).isEqualTo(grantTypes);
  }

  @Test
  public void clientShouldHaveGrantedAuthoritiesGetterAndSetter() {
    Client fixture = new Client();
    fixture.setId(new Random().nextLong());
    Collection<ClientAuthorityAssociation> grantedAuthorities = Arrays
        .asList(new ClientAuthorityAssociation(fixture, new Authority(TEST_CLIENT_AUTHORITY)));

    fixture.setGrantedAuthorities(grantedAuthorities);

    assertThat(fixture).hasFieldOrPropertyWithValue("grantedAuthorities", grantedAuthorities);
    assertThat(fixture.getGrantedAuthorities()).isEqualTo(grantedAuthorities);
  }

  @Test
  public void clientShouldHaveScopesGetterAndSetter() {
    Client fixture = new Client();
    fixture.setId(new Random().nextLong());
    Collection<ClientScopeAssociation> scopes = Arrays
        .asList(new ClientScopeAssociation(fixture, new Scope(TEST_CLIENT_SCOPE)));

    fixture.setScopes(scopes);

    assertThat(fixture).hasFieldOrPropertyWithValue("scopes", scopes);
    assertThat(fixture.getScopes()).isEqualTo(scopes);

  }

  @Test
  public void clientShouldConvertGrantTypeToClientGrantTypeAssociation() {
    List<ClientGrantTypeAssociation> mockAssociation = (List<ClientGrantTypeAssociation>) fixture
        .grantTypesToAssociations(Arrays.asList(new GrantType(TEST_CLIENT_GRANT_TYPE)));

    assertThat(mockAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(mockAssociation.get(0).getClient()).isNotNull().isEqualTo(fixture);
    assertThat(mockAssociation.get(0).getClientGrantType().getName()).isNotNull().isEqualTo(TEST_CLIENT_GRANT_TYPE);
  }

  @Test
  public void clientShouldConvertAuthorityToClientAuthorityAssociation() {
    List<ClientAuthorityAssociation> mockAssociation = (List<ClientAuthorityAssociation>) fixture
        .authoritiesToAssociations(Arrays.asList(new Authority(TEST_CLIENT_AUTHORITY)));

    assertThat(mockAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(mockAssociation.get(0).getClient()).isNotNull().isEqualTo(fixture);
    assertThat(mockAssociation.get(0).getClientAuthority().getName()).isNotNull().isEqualTo(TEST_CLIENT_AUTHORITY);
  }

  @Test
  public void clientShouldConvertScopeToClientScopeAssociation() {
    List<ClientScopeAssociation> mockAssociation = (List<ClientScopeAssociation>) fixture
        .scopesToAssociations(Arrays.asList(new Scope(TEST_CLIENT_SCOPE)));

    assertThat(mockAssociation).isNotNull().isNotEmpty().hasSize(1);
    assertThat(mockAssociation.get(0).getClient()).isNotNull().isEqualTo(fixture);
    assertThat(mockAssociation.get(0).getClientScope().getName()).isNotNull().isEqualTo(TEST_CLIENT_SCOPE);
  }
}