package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.RoleService;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, RoleService.class})
public interface UserMapper extends EntityMapper<User, UserDTO> {

  RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

  @Override
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "confirmation", ignore = true)
  UserDTO toDto(User entity);

  // @formatter:off
  @Override
  @Mapping(target = "roles", expression = "java("
      + "  rolesToAssociations(dto.getRoles(), user)"
      + ")")
  // @formatter:on
  User toEntity(UserDTO dto);

  // @formatter:off
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", expression = "java("
      + "  rolesToAssociations(updated.getRoles(), existing)"
      + ")")
  // @formatter:on
  User updateUserFromUserDTO(UserDTO updated, @MappingTarget User existing);

  default Set<RoleDTO> associationsToRoles(Set<UserRoleAssociation> roles) {
    return roles.stream().map(UserRoleAssociation::getRole).map(roleMapper::toDto)
        .collect(Collectors.toSet());
  }

  default Set<UserRoleAssociation> rolesToAssociations(Set<RoleDTO> roles,
      @MappingTarget User entity) {
    return roles.stream()
        .map(role -> new UserRoleAssociation(entity, ApplicationContextHolder
            .getApplicationContext().getBean(RoleService.class).findByName(role.getName())))
        .collect(Collectors.toSet());
  }
}
