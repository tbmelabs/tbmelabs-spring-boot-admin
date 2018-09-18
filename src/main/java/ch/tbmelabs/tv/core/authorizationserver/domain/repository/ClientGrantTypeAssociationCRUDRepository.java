package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientGrantTypeAssociationCRUDRepository
  extends CrudRepository<ClientGrantTypeAssociation, Long> {

  Set<ClientGrantTypeAssociation> findAllByClient(Client client);
}
