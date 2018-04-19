package ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.stereotype.Component;
import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientDTOMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientAuthorityAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientGrantTypeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.ClientDTOTest;
import ch.tbmelabs.tv.core.authorizationserver.test.web.rest.ClientControllerIntTest;

public class ClientDTOMapperTest {

  @Mock
  private ClientGrantTypeAssociationCRUDRepository mockGrantTypeRepository;

  @Mock
  private ClientAuthorityAssociationCRUDRepository mockAuthorityRepository;

  @Mock
  private ClientScopeAssociationCRUDRepository mockScopeRepository;

  @Spy
  @InjectMocks
  private ClientDTOMapper fixture;

  private Client testClient;
  private ClientDTO testClientDTO;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    testClient = ClientDTOTest.createTestClient();

    testClientDTO = ClientControllerIntTest.createTestClientDTO();
    testClientDTO.setId(new Random().nextLong());

    doReturn(new ArrayList<>()).when(mockGrantTypeRepository).findAllByClient(testClient);
    doReturn(new ArrayList<>()).when(mockAuthorityRepository).findAllByClient(testClient);
    doReturn(new ArrayList<>()).when(mockScopeRepository).findAllByClient(testClient);
  }

  @Test
  public void clientDTOMapperShouldBeAnnotated() {
    assertThat(ClientDTOMapper.class).hasAnnotation(Component.class);
  }

  @Test
  public void clientDTOMapperShouldHavePublicConstructor() {
    assertThat(new ClientDTOMapper()).isNotNull();
  }

  @Test
  public void toClientDTOShouldMapClientToDTO() {
    assertThat(fixture.toClientDTO(testClient))
        .hasFieldOrPropertyWithValue("created", testClient.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testClient.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testClient.getId())
        .hasFieldOrPropertyWithValue("clientId", testClient.getClientId())
        .hasFieldOrPropertyWithValue("isSecretRequired", testClient.getIsSecretRequired())
        .hasFieldOrPropertyWithValue("isAutoApprove", testClient.getIsAutoApprove())
        .hasFieldOrPropertyWithValue("accessTokenValiditySeconds",
            testClient.getAccessTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("refreshTokenValiditySeconds",
            testClient.getRefreshTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("redirectUri", testClient.getRedirectUri())
        .hasFieldOrPropertyWithValue("grantTypes", new ArrayList<>())
        .hasFieldOrPropertyWithValue("grantedAuthorities", new ArrayList<>())
        .hasFieldOrPropertyWithValue("scopes", new ArrayList<>());

    assertThat(fixture.toClientDTO(testClient).getSecret()).isNull();
  }

  @Test
  public void toClientShouldMapDTOToEntity() {
    assertThat(fixture.toClient(testClientDTO))
        .hasFieldOrPropertyWithValue("created", testClientDTO.getCreated())
        .hasFieldOrPropertyWithValue("lastUpdated", testClientDTO.getLastUpdated())
        .hasFieldOrPropertyWithValue("id", testClientDTO.getId())
        .hasFieldOrPropertyWithValue("clientId", testClientDTO.getClientId())
        .hasFieldOrPropertyWithValue("secret", testClientDTO.getSecret())
        .hasFieldOrPropertyWithValue("isSecretRequired", testClientDTO.getIsSecretRequired())
        .hasFieldOrPropertyWithValue("isAutoApprove", testClientDTO.getIsAutoApprove())
        .hasFieldOrPropertyWithValue("accessTokenValiditySeconds",
            testClientDTO.getAccessTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("refreshTokenValiditySeconds",
            testClientDTO.getRefreshTokenValiditySeconds())
        .hasFieldOrPropertyWithValue("redirectUri", testClientDTO.getRedirectUri())
        // TODO: Add value check
        .hasFieldOrProperty("grantTypes")
        // TODO: Add value check
        .hasFieldOrProperty("grantedAuthorities")
        // TODO: Add value check
        .hasFieldOrProperty("scopes");
  }
}
