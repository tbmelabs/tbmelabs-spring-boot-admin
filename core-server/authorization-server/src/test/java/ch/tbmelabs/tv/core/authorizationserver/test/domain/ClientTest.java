package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Table;

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
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientrole.ClientAuthorityAssociation;
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

    when(fixture.getId()).thenReturn(new Random().nextLong());

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
  public void newClientShouldHaveDefaultValues() {
    Client fixture = new Client();

    assertThat(fixture.getIsSecretRequired()).isTrue();
    assertThat(fixture.getIsAutoApprove()).isFalse();
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