package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Scope;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ScopeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScopeMapper extends EntityMapper<Scope, ScopeDTO> {

}
