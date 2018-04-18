package ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserProfile;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;

@Component
public class UserProfileMapper {
  @Autowired
  private UserRoleAssociationCRUDRepository userRoleAssociationRepository;

  public UserProfile toUserProfile(User user) {
    return new UserProfile(user, userRoleAssociationRepository.findAllByUser(user).stream()
        .map(UserRoleAssociation::getUserRole).collect(Collectors.toList()));
  }

  public User toUser(UserProfile userProfile) {
    User user = new User();

    user.setCreated(userProfile.getCreated());
    user.setLastUpdated(userProfile.getLastUpdated());
    user.setId(userProfile.getId());
    user.setUsername(userProfile.getUsername());
    user.setEmail(userProfile.getEmail());
    user.setIsEnabled(userProfile.getIsEnabled() != null ? userProfile.getIsEnabled()
        : new User().getIsEnabled());
    user.setIsBlocked(userProfile.getIsBlocked() != null ? userProfile.getIsBlocked()
        : new User().getIsBlocked());

    final List<UserRoleAssociation> userRoleAssociations = new ArrayList<>();
    if (user.getId() != null) {
      userRoleAssociations.addAll(userRoleAssociationRepository.findAllByUser(user));
    }
    if (userProfile.getRoles() != null) {
      userProfile.getRoles().stream()
          .filter(userProfileRole -> userRoleAssociations.stream()
              .noneMatch(existingRole -> existingRole.getUserRole().getName()
                  .equals(userProfileRole.getName())))
          .map(user::roleToAssociation).forEach(userRoleAssociations::add);
      user.setRoles(userRoleAssociations);
    }

    return user;
  }
}
