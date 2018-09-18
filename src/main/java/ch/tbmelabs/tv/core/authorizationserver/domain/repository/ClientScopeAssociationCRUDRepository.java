package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientScopeAssociationCRUDRepository
  extends CrudRepository<ClientScopeAssociation, Long> {

  Set<ClientScopeAssociation> findAllByClient(Client client);
}
