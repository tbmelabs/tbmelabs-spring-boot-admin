package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPBlacklistCRUDRepository extends CrudRepository<BlacklistedIp, Long> {

  Optional<BlacklistedIp> findOneByStartIpLessThanAndEndIpGreaterThan(String startIp, String endIp);
}
