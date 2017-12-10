package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserCRUDRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user;

    if ((user = userRepository.findByUsername(username)) == null) {
      throw new UsernameNotFoundException("Username " + username + " does not exist!");
    }

    return new UserPrincipal(user);
  }
}