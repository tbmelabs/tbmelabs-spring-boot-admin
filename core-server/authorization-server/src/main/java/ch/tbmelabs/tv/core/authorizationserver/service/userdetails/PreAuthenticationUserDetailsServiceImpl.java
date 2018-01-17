package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class PreAuthenticationUserDetailsServiceImpl
    implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) {
    return ((UserDetails) ((UsernamePasswordAuthenticationToken) token.getPrincipal()).getPrincipal());
  }
}