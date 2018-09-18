package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthorityAssociationCRUDRepository
  extends CrudRepository<ClientAuthorityAssociation, Long> {

  Set<ClientAuthorityAssociation> findAllByClient(Client client);
}
