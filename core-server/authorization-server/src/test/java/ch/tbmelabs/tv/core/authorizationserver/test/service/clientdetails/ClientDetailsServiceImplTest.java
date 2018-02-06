package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;

public class ClientDetailsServiceImplTest {
  @Mock
  private ClientCRUDRepository clientRepositoryFixture;

  @Spy
  @InjectMocks
  private ClientDetailsServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(clientRepositoryFixture.findByClientId(Mockito.anyString())).thenReturn(new Client());
  }

  @Test
  public void clientDetailsServiceShouldReturnCorrectClientDetailsForId()
      throws IllegalAccessException, NoSuchFieldException, SecurityException {
    ClientDetailsImpl clientDetails = fixture.loadClientByClientId(UUID.randomUUID().toString());

    assertThat(ReflectionTestUtils.getField(clientDetails, "client"))
        .isEqualTo(clientRepositoryFixture.findByClientId(UUID.randomUUID().toString()));
  }
}