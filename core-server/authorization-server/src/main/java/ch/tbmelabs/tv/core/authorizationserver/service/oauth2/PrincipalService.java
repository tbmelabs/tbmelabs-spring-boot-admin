package ch.tbmelabs.tv.core.authorizationserver.service.oauth2;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserRoleAssociationCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Service
public class PrincipalService {
  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private UserRoleAssociationCRUDRepository userRoleRepository;

  public User getCurrentUser() {
    if (!isAuthenticated()) {
      return null;
    }

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User currentUser;
    if ((currentUser = userRepository.findByUsername(username)) == null) {
      throw new IllegalArgumentException("Cannot identify authenticated user: Please try again!");
    }

//    currentUser.setRoles(
//        ((List<UserRoleAssociation>) IteratorUtils.toList(userRoleRepository.findAllByUser(currentUser).iterator()))
//            .stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList()));

    return currentUser;
  }

  public static boolean isAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .noneMatch(role -> role.getAuthority().equals(new Role(SecurityRole.ANONYMOUS).getAuthority()));
  }
}