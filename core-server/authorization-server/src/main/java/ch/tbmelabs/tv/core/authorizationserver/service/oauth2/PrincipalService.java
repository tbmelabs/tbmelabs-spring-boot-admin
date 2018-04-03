package ch.tbmelabs.tv.core.authorizationserver.service.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

@Service
public class PrincipalService {
  @Autowired
  private UserCRUDRepository userRepository;

  public User getCurrentUser() {
    if (!isAuthenticated()) {
      return null;
    }

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Optional<User> currentUser;
    if (!(currentUser = userRepository.findOneByUsername(username)).isPresent()) {
      throw new IllegalArgumentException("Cannot identify authenticated user: Please try again!");
    }

    return currentUser.get();
  }

  public static boolean isAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .noneMatch(role -> role.getAuthority().equals(new Role(UserAuthority.ANONYMOUS).getAuthority()));
  }
}