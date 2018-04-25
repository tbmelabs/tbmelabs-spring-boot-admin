package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {GrantTypeMapper.class, AuthorityMapper.class, ScopeMapper.class})
public interface ClientMapper extends EntityMapper<Client, ClientDTO> {

}
