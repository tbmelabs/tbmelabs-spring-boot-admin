package ch.tbmelabs.tv.core.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.shared.resources.authentication.bruteforcing.BlacklistedIp;

@Repository
public interface IPBlacklistCRUDRepository extends CrudRepository<BlacklistedIp, Long> {
}