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
import java.util.Collection;
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
  @Mapping(target = "secret", ignore = true)
  @Mapping(source = "redirectUri", target = "redirectUris")
  ClientDTO toDto(Client entity);

  @Override
  @Mapping(source = "redirectUris", target = "redirectUri")
  Client toEntity(ClientDTO dto);

  default String[] redirectUriToRedirectUris(String redirectUri) {
    return redirectUri.split(Client.REDIRECT_URI_SPLITTERATOR);
  }

  default String redirectUrisToRedirectUri(String[] redirectUris) {
    return Strings.join(Arrays.asList(redirectUris), Client.REDIRECT_URI_SPLITTERATOR.charAt(0));
  }

  default Collection<GrantTypeDTO> grantTypeAssociationsToGrantTypes(
      Collection<ClientGrantTypeAssociation> grantTypes) {
    return grantTypes.stream().map(ClientGrantTypeAssociation::getClientGrantType)
        .map(grantTypeMapper::toDto).collect(Collectors.toList());
  }

  default Collection<ClientGrantTypeAssociation> grantTypesToGrantTypeAssociations(
      Collection<GrantTypeDTO> grantTypes, @MappingTarget Client client) {
    return grantTypes.stream().map(
        grantType -> new ClientGrantTypeAssociation(client, grantTypeMapper.toEntity(grantType)))
        .collect(Collectors.toList());
  }

  default Collection<AuthorityDTO> authorityAssociationsToAuthorities(
      Collection<ClientAuthorityAssociation> grantedAuthorities) {
    return grantedAuthorities.stream().map(ClientAuthorityAssociation::getClientAuthority)
        .map(authorityMapper::toDto).collect(Collectors.toList());
  }

  default Collection<ClientAuthorityAssociation> authoritiesToAuthorityAssociations(
      Collection<AuthorityDTO> authorities, @MappingTarget Client client) {
    return authorities.stream().map(
        authority -> new ClientAuthorityAssociation(client, authorityMapper.toEntity(authority)))
        .collect(Collectors.toList());
  }

  default Collection<ScopeDTO> scopeAssociationsToScopes(
      Collection<ClientScopeAssociation> scopes) {
    return scopes.stream().map(ClientScopeAssociation::getClientScope).map(scopeMapper::toDto)
        .collect(Collectors.toList());
  }

  default Collection<ClientScopeAssociation> scopesToScopeAssociations(Collection<ScopeDTO> scopes,
      @MappingTarget Client client) {
    return scopes.stream()
        .map(scope -> new ClientScopeAssociation(client, scopeMapper.toEntity(scope)))
        .collect(Collectors.toList());
  }
}
