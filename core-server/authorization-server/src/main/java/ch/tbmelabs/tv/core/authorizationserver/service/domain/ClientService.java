package ch.tbmelabs.tv.core.authorizationserver.service.domain;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

  Client save(ClientDTO clientDTO);

  Page<ClientDTO> findAll(Pageable pageable);

  Optional<Client> findOneById(Long id);

  Client update(ClientDTO clientDTO);

  void delete(Long id);
}
