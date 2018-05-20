package ch.tbmelabs.tv.core.authorizationserver.web.rest;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.ClientMapper;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.ClientService;
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
  private ClientService clientService;

  @Autowired
  private ClientMapper clientMapper;

  @PostMapping
  public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
    return clientMapper.toDto(clientService.save(clientDTO));
  }

  @GetMapping
  public Page<ClientDTO> getAllClients(Pageable pageable) {
    return clientService.findAll(pageable);
  }

  @PutMapping
  public ClientDTO updateClient(@RequestBody ClientDTO clientDTO) {
    return clientMapper.toDto(clientService.update(clientDTO));
  }

  @DeleteMapping
  public void deleteClient(@RequestBody ClientDTO clientDTO) {
    clientService.delete(clientDTO);
  }
}
