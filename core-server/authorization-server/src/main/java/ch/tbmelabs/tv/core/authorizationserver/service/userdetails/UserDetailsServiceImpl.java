package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

  private UserCRUDRepository userRepository;

  public UserDetailsServiceImpl(UserCRUDRepository userCRUDRepository) {
    this.userRepository = userCRUDRepository;
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    LOGGER.debug("Loading userdetails for username \"" + username + "\"");

    Optional<User> user;
    if (!(user = userRepository.findOneByUsernameIgnoreCase(username)).isPresent()) {
      throw new UsernameNotFoundException("Username " + username + " does not exist!");
    }

    return new UserDetailsImpl(user.get());
  }
}
