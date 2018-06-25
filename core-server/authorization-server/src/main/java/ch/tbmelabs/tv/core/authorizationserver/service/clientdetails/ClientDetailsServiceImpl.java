package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

  private static final Logger LOGGER = LogManager.getLogger(ClientDetailsServiceImpl.class);

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Override
  public ClientDetailsImpl loadClientByClientId(String clientId) {
    LOGGER.debug("Loading client details for client id \"" + clientId + "\"");

    Optional<Client> client;
    if (!(client = clientRepository.findOneByClientId(clientId)).isPresent()) {
      throw new IllegalArgumentException("Client with ID " + clientId + " does not exist!");
    }

    return new ClientDetailsImpl(client.get());
  }
}
