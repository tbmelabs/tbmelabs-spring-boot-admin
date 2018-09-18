package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.AuthorityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<Authority, AuthorityDTO> {

}
