package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2ApprovalClientScopesController {

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private ClientScopeAssociationCRUDRepository clientScopeAssociationRepository;

  @RequestMapping("/oauth/confirm_access_scopes")
  public List<String> getAccessConfirmation(
      @RequestParam(name = "client_id", required = true) String clientId) {
    Optional<Client> client;
    if (!(client = clientRepository.findOneByClientId(clientId)).isPresent()) {
      throw new IllegalArgumentException("Invalid client-id \"" + clientId + "\"!");
    }

    return clientScopeAssociationRepository
        .findAllByClient(client.get()).stream()
        .map(association -> association.getClientScope().getName()).collect(Collectors.toList());
  }
}
