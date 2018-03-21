package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientDTOMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@RestController
@PreAuthorize("hasRole('" + SecurityRole.SERVER_ADMIN + "')")
@RequestMapping({ "${spring.data.rest.base-path}/clients" })
public class ClientController {
  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private ClientDTOMapper clientMapper;

  @PostMapping
  public ClientDTO createClient(@RequestBody(required = true) ClientDTO clientDTO) {
    if (clientDTO.getId() != 0) {
      throw new IllegalArgumentException("You can only create a new client without an id!");
    }

    return clientMapper.toClientDTO(clientRepository.save(clientMapper.toClient(clientDTO)));
  }

  @GetMapping
  @SuppressWarnings("unchecked")
  public List<ClientDTO> getAllClients() {
    return ((List<Client>) IteratorUtils.toList(clientRepository.findAll().iterator())).stream()
        .map(clientMapper::toClientDTO).collect(Collectors.toList());
  }

  @PutMapping
  public ClientDTO updateClient(@RequestBody(required = true) ClientDTO clientDTO) {
    if (clientDTO.getId() == 0) {
      throw new IllegalArgumentException("You can only update an existing client!");
    }

    return clientMapper.toClientDTO(clientRepository.save(clientMapper.toClient(clientDTO)));
  }

  @DeleteMapping
  public void deleteClient(@RequestBody(required = true) ClientDTO clientDTO) {
    if (clientDTO.getId() == 0) {
      throw new IllegalArgumentException("You can only delete an existing client!");
    }

    clientRepository.delete(clientMapper.toClient(clientDTO));
  }
}