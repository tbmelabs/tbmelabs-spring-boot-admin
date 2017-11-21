package ch.tbmelabs.tv.core.authenticationserver.service.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authenticationserver.domain.repository.UserCRUDRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserCRUDRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserPrincipal(userRepository.findByUsername(username));
  }
}