package ch.tbmelabs.tv.core.authorizationserver.service.domain.impl;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.ClientService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

  private ClientMapper clientMapper;

  private ClientCRUDRepository clientRepository;

  public ClientServiceImpl(ClientMapper clientMapper, ClientCRUDRepository clientCRUDRepository) {
    this.clientMapper = clientMapper;
    this.clientRepository = clientCRUDRepository;
  }

  @Transactional
  public Client save(ClientDTO clientDTO) {
    if (clientDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new client without an id!");
    }

    return clientRepository.save(clientMapper.toEntity(clientDTO));
  }

  public Page<ClientDTO> findAll(Pageable pageable) {
    return clientRepository.findAll(pageable).map(clientMapper::toDto);
  }

  public Optional<Client> findOneById(Long id) {
    return clientRepository.findById(id);
  }

  @Transactional
  public Client update(ClientDTO clientDTO) {
    Optional<Client> existing;
    if (clientDTO.getId() == null
        || (existing = clientRepository.findById(clientDTO.getId())) == null) {
      throw new IllegalArgumentException("You can only update an existing client!");
    }

    return clientRepository.save(clientMapper.updateClientFromClientDto(clientDTO, existing.get()));
  }

  public void delete(Long id) {
    clientRepository.deleteById(id);
  }
}
