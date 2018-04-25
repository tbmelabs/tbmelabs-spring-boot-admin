package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {

}
