package ch.tbmelabs.tv.core.authenticationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.shared.domain.authentication.client.Client;

@Repository
public interface ClientCRUDRepository extends CrudRepository<Client, Long> {
  Client findByClientId(String clientId);
}