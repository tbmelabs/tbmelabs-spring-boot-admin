package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

public class ClientDetailsServiceImplTest {

  @Mock
  private ClientCRUDRepository mockClientRepository;

  @Spy
  @InjectMocks
  private ClientDetailsServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(Optional.of(new Client())).when(mockClientRepository)
      .findByClientId(ArgumentMatchers.anyString());
  }

  @Test
  public void clientDetailsServiceImplShouldBeAnnotated() {
    assertThat(ClientDetailsServiceImpl.class).hasAnnotation(Service.class);
  }

  @Test
  public void clientDetailsServiceImplShouldImplementClientDetailsService() {
    assertThat(ClientDetailsService.class).isAssignableFrom(ClientDetailsServiceImpl.class);
  }

  @Test
  public void clientDetailsServiceImplConstructorShouldAcceptRepository() {
    assertThat(new ClientDetailsServiceImpl(mockClientRepository)).isNotNull();
  }

  @Test
  public void loadClientByClientIdShouldLoadCorrectClient()
    throws SecurityException {
    ClientDetailsImpl clientDetails = fixture.loadClientByClientId(UUID.randomUUID().toString());

    assertThat(ReflectionTestUtils.getField(clientDetails, "client"))
      .isEqualTo(mockClientRepository.findByClientId(UUID.randomUUID().toString()).get());
  }

  @Test
  public void loadClientShouldThrowExceptionIfClientIdDoesNotExist() {
    doReturn(Optional.empty()).when(mockClientRepository)
      .findByClientId(ArgumentMatchers.anyString());

    assertThatThrownBy(() -> fixture.loadClientByClientId(RandomStringUtils.random(11)))
      .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Client with ID")
      .hasMessageContaining("does not exist!");
  }
}
