package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import java.util.Arrays;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {GrantTypeMapper.class, AuthorityMapper.class, ScopeMapper.class})
public interface ClientMapper extends EntityMapper<Client, ClientDTO> {

  default String[] redirectUriToRedirectUris(String redirectUri) {
    return redirectUri.split(Client.REDIRECT_URI_SPLITTERATOR);
  }

  default String redirectUrisToRedirectUri(String[] redirectUris) {
    return Strings.join(Arrays.asList(redirectUris), Client.REDIRECT_URI_SPLITTERATOR.charAt(0));
  }
}
