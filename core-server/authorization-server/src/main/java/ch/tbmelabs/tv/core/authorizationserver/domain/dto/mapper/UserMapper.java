package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<User, UserDTO> {

  RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

  @Override
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "confirmation", ignore = true)
  UserDTO toDto(User entity);

  User updateUserFromUser(User updated, @MappingTarget User existing);

  default Set<RoleDTO> associationsToRoles(Set<UserRoleAssociation> roles) {
    return roles.stream().map(UserRoleAssociation::getUserRole).map(roleMapper::toDto)
        .collect(Collectors.toSet());
  }

  default Set<UserRoleAssociation> rolesToAssociations(Set<RoleDTO> roles,
      @MappingTarget User entity) {
    return roles.stream().map(role -> new UserRoleAssociation(entity, roleMapper.toEntity(role)))
        .collect(Collectors.toSet());
  }
}
