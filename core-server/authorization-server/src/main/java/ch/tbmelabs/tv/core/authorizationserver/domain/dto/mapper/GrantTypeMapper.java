package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.GrantTypeDTO;
import org.mapstruct.Mapper;

@Mapper
public interface GrantTypeMapper extends EntityMapper<GrantType, GrantTypeDTO> {

}
