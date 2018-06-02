package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.Client;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientauthority.ClientAuthorityAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientgranttype.ClientGrantTypeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.clientscope.ClientScopeAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.AuthorityDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ClientDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.GrantTypeDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.ScopeDTO;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
    uses = {GrantTypeMapper.class, AuthorityMapper.class, ScopeMapper.class})
public interface ClientMapper extends EntityMapper<Client, ClientDTO> {

  GrantTypeMapper grantTypeMapper = Mappers.getMapper(GrantTypeMapper.class);
  AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);
  ScopeMapper scopeMapper = Mappers.getMapper(ScopeMapper.class);

  @Override
  @Mapping(source = "redirectUri", target = "redirectUris")
  ClientDTO toDto(Client entity);

  @Override
  @Mapping(source = "redirectUris", target = "redirectUri")
  Client toEntity(ClientDTO dto);

  Client updateClientFromClient(Client updated, @MappingTarget Client existing);

  default String[] redirectUriToRedirectUris(String redirectUri) {
    return redirectUri.split(Client.REDIRECT_URI_SPLITTERATOR);
  }

  default String redirectUrisToRedirectUri(String[] redirectUris) {
    return Strings.join(Arrays.asList(redirectUris), Client.REDIRECT_URI_SPLITTERATOR.charAt(0));
  }

  default Set<GrantTypeDTO> grantTypeAssociationsToGrantTypes(
      Set<ClientGrantTypeAssociation> grantTypes) {
    return grantTypes.stream().map(ClientGrantTypeAssociation::getClientGrantType)
        .map(grantTypeMapper::toDto).collect(Collectors.toSet());
  }

  default Set<ClientGrantTypeAssociation> grantTypesToGrantTypeAssociations(
      Set<GrantTypeDTO> grantTypes, @MappingTarget Client client) {
    return grantTypes.stream().map(
        grantType -> new ClientGrantTypeAssociation(client, grantTypeMapper.toEntity(grantType)))
        .collect(Collectors.toSet());
  }

  default Set<AuthorityDTO> authorityAssociationsToAuthorities(
      Set<ClientAuthorityAssociation> grantedAuthorities) {
    return grantedAuthorities.stream().map(ClientAuthorityAssociation::getClientAuthority)
        .map(authorityMapper::toDto).collect(Collectors.toSet());
  }

  default Set<ClientAuthorityAssociation> authoritiesToAuthorityAssociations(
      Set<AuthorityDTO> authorities, @MappingTarget Client client) {
    return authorities.stream().map(
        authority -> new ClientAuthorityAssociation(client, authorityMapper.toEntity(authority)))
        .collect(Collectors.toSet());
  }

  default Set<ScopeDTO> scopeAssociationsToScopes(Set<ClientScopeAssociation> scopes) {
    return scopes.stream().map(ClientScopeAssociation::getClientScope).map(scopeMapper::toDto)
        .collect(Collectors.toSet());
  }

  default Set<ClientScopeAssociation> scopesToScopeAssociations(Set<ScopeDTO> scopes,
      @MappingTarget Client client) {
    return scopes.stream()
        .map(scope -> new ClientScopeAssociation(client, scopeMapper.toEntity(scope)))
        .collect(Collectors.toSet());
  }
}
