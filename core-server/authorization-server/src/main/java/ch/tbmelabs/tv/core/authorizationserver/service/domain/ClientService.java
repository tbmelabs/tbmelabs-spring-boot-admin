package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientAuthorityAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientGrantTypeAssociationCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientScopeAssociationCRUDRepository;
import java.util.Optional;
import java.util.Set;
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
  private ClientGrantTypeAssociationCRUDRepository clientGrantTypeRepository;

  @Autowired
  private ClientAuthorityAssociationCRUDRepository clientAuthorityRepository;

  @Autowired
  private ClientScopeAssociationCRUDRepository clientScopeRepository;

  @Transactional
  public Client save(ClientDTO clientDTO) {
    if (clientDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new client without an id!");
    }

    Client client = clientMapper.toEntity(clientDTO);
    client = clientRepository.save(client);

    clientMapper.grantTypesToGrantTypeAssociations(clientDTO.getGrantTypes(), client)
        .forEach(clientGrantTypeRepository::save);
    clientMapper.authoritiesToAuthorityAssociations(clientDTO.getGrantedAuthorities(), client)
        .forEach(clientAuthorityRepository::save);
    clientMapper.scopesToScopeAssociations(clientDTO.getScopes(), client)
        .forEach(clientScopeRepository::save);

    return client;
  }

  public Page<ClientDTO> findAll(Pageable pageable) {
    return clientRepository.findAll(pageable).map(clientMapper::toDto);
  }

  public Optional<Client> findOneById(Long id) {
    return Optional.ofNullable(clientRepository.findOne(id));
  }

  @Transactional
  public Client update(ClientDTO clientDTO) {
    if (clientDTO.getId() == null || clientRepository.findOne(clientDTO.getId()) == null) {
      throw new IllegalArgumentException("You can only update an existing client!");
    }

    final Client client = clientRepository.save(clientMapper.updateClientFromClient(
        clientRepository.findOne(clientDTO.getId()), clientMapper.toEntity(clientDTO)));

    Set<ClientGrantTypeAssociation> existingGrantTypes =
        clientGrantTypeRepository.findAllByClient(client);
    Set<ClientGrantTypeAssociation> newGrantTypes =
        clientMapper.grantTypesToGrantTypeAssociations(clientDTO.getGrantTypes(), client);
    // Save new by none match of new in existing
    newGrantTypes.stream()
        .filter(newGrantType -> existingGrantTypes.stream().noneMatch(
            grantType -> grantType.getClientGrantType().equals(newGrantType.getClientGrantType())))
        .forEach(clientGrantTypeRepository::save);
    // Remove deleted by none match of existing in new
    existingGrantTypes.stream()
        .filter(grantType -> newGrantTypes.stream().noneMatch(
            newGrantType -> newGrantType.getClientGrantType().equals(grantType.getClientGrantType())))
        .forEach(clientGrantTypeRepository::delete);

    Set<ClientAuthorityAssociation> existingAuthorities =
        clientAuthorityRepository.findAllByClient(client);
    Set<ClientAuthorityAssociation> newAuthorities =
        clientMapper.authoritiesToAuthorityAssociations(clientDTO.getGrantedAuthorities(), client);
    // Save new by none match of new in existing
    newAuthorities.stream()
        .filter(newAuthority -> existingAuthorities.stream().noneMatch(
            authority -> authority.getClientAuthority().equals(newAuthority.getClientAuthority())))
        .forEach(clientAuthorityRepository::save);
    // Remove deleted by none match of existing in new
    existingAuthorities.stream()
        .filter(authority -> newAuthorities.stream().noneMatch(
            newAuthority -> newAuthority.getClientAuthority().equals(authority.getClientAuthority())))
        .forEach(clientAuthorityRepository::delete);

    Set<ClientScopeAssociation> existingScopes = clientScopeRepository.findAllByClient(client);
    Set<ClientScopeAssociation> newScopes =
        clientMapper.scopesToScopeAssociations(clientDTO.getScopes(), client);
    // Save new by none match of new in existing
    newScopes.stream()
        .filter(newScope -> existingScopes.stream().noneMatch(
            scope -> scope.getClientScope().equals(newScope.getClientScope())))
        .forEach(clientScopeRepository::save);
    // Remove deleted by none match of existing in new
    existingScopes.stream()
        .filter(scope -> newScopes.stream().noneMatch(
            newScope -> newScope.getClientScope().equals(scope.getClientScope())))
        .forEach(clientScopeRepository::delete);

    return client;
  }

  public void delete(Long id) {
    clientRepository.delete(id);
  }
}
