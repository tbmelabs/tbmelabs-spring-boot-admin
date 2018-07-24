package ch.tbmelabs.tv.shared.securityutils.authentication.manager.provider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationProviderFactory {

  public static AuthenticationProvider buildAuthenticationProvider(
      AuthenticationManager authenticationManager) {
    return new AuthenticationProvider() {

      @Override
      public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
      }

      @Override
      public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {
        return authenticationManager.authenticate(authentication);
      }
    };
  }

  private AuthenticationProviderFactory() {

  }
}
