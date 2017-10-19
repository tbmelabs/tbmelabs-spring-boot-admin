package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;

import ch.tbmelabs.tv.resource.authorization.client.Client;

public interface ClientCRUDRepository extends CrudRepository<Client, Long> {
  Client findByName(String name);
}