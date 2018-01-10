package ch.tbmelabs.tv.core.authorizationserver.service.clientdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
  @Autowired
  private ClientCRUDRepository clientRepository;

  @Override
  public Client loadClientByClientId(String clientId) {
    return clientRepository.findByClientId(clientId);
  }
}