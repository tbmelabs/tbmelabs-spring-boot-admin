package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.ClientCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"${spring.data.rest.base-path}/clients"})
@PreAuthorize("hasAuthority('" + UserAuthority.SERVER_ADMIN + "')")
public class ClientController {

  @Autowired
  private ClientCRUDRepository clientRepository;

  @Autowired
  private ClientMapper clientMapper;

  @PostMapping
  public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
    if (clientDTO.getId() != null) {
      throw new IllegalArgumentException("You can only create a new client without an id!");
    }

    return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDTO)));
  }

  @GetMapping
  public Page<ClientDTO> getAllClients(Pageable pageable) {
    return clientRepository.findAll(pageable).map(clientMapper::toDto);
  }

  @PutMapping
  public ClientDTO updateClient(@RequestBody ClientDTO clientDTO) {
    if (clientDTO.getId() == null || clientRepository.findOne(clientDTO.getId()) == null) {
      throw new IllegalArgumentException("You can only update an existing client!");
    }

    return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDTO)));
  }

  @DeleteMapping
  public void deleteClient(@RequestBody ClientDTO clientDTO) {
    if (clientDTO.getId() == null) {
      throw new IllegalArgumentException("You can only delete an existing client!");
    }

    clientRepository.delete(clientMapper.toEntity(clientDTO));
  }
}
