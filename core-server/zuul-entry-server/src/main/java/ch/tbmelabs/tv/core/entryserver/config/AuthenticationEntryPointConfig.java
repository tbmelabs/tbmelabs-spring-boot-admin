package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import ch.tbmelabs.tv.core.security.authentication.AdvancedLoginUrlAuthenticationPoint;

// @Configuration
public class AuthenticationEntryPointConfig {
  @Value("${security.oauth2.sso.login-path}")
  private String ssoLoginPath;

  @Bean
  @Profile("!dev")
  public AdvancedLoginUrlAuthenticationPoint authenticationEntryPoint() {
    AdvancedLoginUrlAuthenticationPoint entryPoint = new AdvancedLoginUrlAuthenticationPoint(ssoLoginPath);
    entryPoint.setForceHttps(true);

    return entryPoint;
  }

  @Bean
  @Profile("dev")
  public AdvancedLoginUrlAuthenticationPoint authenticationDevEntryPoint() {
    return new AdvancedLoginUrlAuthenticationPoint(ssoLoginPath);
  }
}