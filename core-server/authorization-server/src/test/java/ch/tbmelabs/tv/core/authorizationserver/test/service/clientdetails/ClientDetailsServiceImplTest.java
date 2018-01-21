package ch.tbmelabs.tv.core.authorizationserver.test.service.clientdetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

@Transactional
public class ClientDetailsServiceImplTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private Client testClient;

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Before
  public void beforeTestSetUp() {
    Client newClient = new Client();
    newClient.setClientId(UUID.randomUUID().toString());
    newClient.setAccessTokenValiditySeconds(3600);
    newClient.setRefreshTokenValiditySeconds(7200);

    testClient = clientRepository.save(newClient);
  }

  @Test
  public void clientDetailsServiceShouldReturnCorrectClientDetailsForId()
      throws IllegalAccessException, NoSuchFieldException, SecurityException {
    ClientDetailsImpl clientDetails = clientDetailsService.loadClientByClientId(testClient.getClientId());

    Field clientField = ClientDetailsImpl.class.getDeclaredField("client");
    clientField.setAccessible(true);
    assertThat(((Client) clientField.get(clientDetails)).getId()).isNotNull().isEqualTo(testClient.getId());
  }
}