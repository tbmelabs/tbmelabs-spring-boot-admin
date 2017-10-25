package ch.tbmelabs.tv.authenticationserver.resource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.resource.authentication.bruteforcing.BlacklistedIp;

@Repository
public interface IPBlacklistCRUDRepository extends CrudRepository<BlacklistedIp, Long> {
}