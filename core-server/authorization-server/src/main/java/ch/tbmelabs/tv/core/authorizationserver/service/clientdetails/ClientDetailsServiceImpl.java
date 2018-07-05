package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);

  private ClientCRUDRepository clientRepository;

  public ClientDetailsServiceImpl(ClientCRUDRepository clientCRUDRepository) {
    this.clientRepository = clientCRUDRepository;
  }

  @Override
  public ClientDetailsImpl loadClientByClientId(String clientId) {
    LOGGER.debug("Loading client details for client id \'{}\'", clientId);

    Optional<Client> client;
    if (!(client = clientRepository.findOneByClientId(clientId)).isPresent()) {
      throw new IllegalArgumentException("Client with ID \'" + clientId + "\' does not exist!");
    }

    return new ClientDetailsImpl(client.get());
  }
}
