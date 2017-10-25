package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.resource.authentication.client.Client;

@Repository
public interface ClientCRUDRepository extends CrudRepository<Client, Long> {
  Client findByName(String name);
}