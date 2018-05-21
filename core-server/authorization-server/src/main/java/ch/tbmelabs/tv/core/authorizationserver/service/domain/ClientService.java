package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.GrantTypeCRUDRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  private ClientMapper clientMapper;

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private GrantTypeCRUDRepository grantTypeRepository;

  @Transactional
  public Client save(ClientDTO clientDTO) {
    if (clientDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new client without an id!");
    }

    Client client = clientMapper.toEntity(clientDTO);
    client = clientRepository.save(client);

    client.setGrantTypes(
        clientMapper.grantTypesToGrantTypeAssociations(clientDTO.getGrantTypes(), client));
    client.setGrantedAuthorities(
        clientMapper.authoritiesToAuthorityAssociations(clientDTO.getGrantedAuthorities(), client));
    client.setScopes(clientMapper.scopesToScopeAssociations(clientDTO.getScopes(), client));

    return clientRepository.save(client);
  }

  public Page<ClientDTO> findAll(Pageable pageable) {
    return clientRepository.findAll(pageable).map(clientMapper::toDto);
  }

  public Client update(ClientDTO clientDTO) {
    if (clientDTO.getId() == null || clientRepository.findOne(clientDTO.getId()) == null) {
      throw new IllegalArgumentException("You can only update an existing client!");
    }

    return save(clientDTO);
  }

  public void delete(ClientDTO clientDTO) {
    if (clientDTO.getId() == null) {
      throw new IllegalArgumentException("You can only delete an existing client!");
    }

    clientRepository.delete(clientMapper.toEntity(clientDTO));
  }
}
