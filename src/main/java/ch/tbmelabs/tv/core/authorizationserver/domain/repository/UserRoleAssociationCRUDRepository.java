package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleAssociationCRUDRepository
    extends CrudRepository<UserRoleAssociation, Long> {

  Set<UserRoleAssociation> findAllByUser(User user);
}
