package ch.tbmelabs.tv.authenticationserver.clientdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.authenticationserver.resource.repository.ClientCRUDRepository;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
  @Autowired
  private ClientCRUDRepository clientRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return clientRepository.findByName(clientId);
  }
}