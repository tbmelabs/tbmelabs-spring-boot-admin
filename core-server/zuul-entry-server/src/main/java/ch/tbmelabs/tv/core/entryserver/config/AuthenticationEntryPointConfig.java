package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class AuthenticationEntryPointConfig {
  @Value("${security.oauth2.sso.login-path}")
  private String ssoLoginPath;

  @Bean
  @Profile("!dev")
  public LoginUrlAuthenticationEntryPoint authenticationEntryPoint() {
    LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(ssoLoginPath);
    entryPoint.setForceHttps(true);

    return entryPoint;
  }

  @Bean
  @Profile("dev")
  public LoginUrlAuthenticationEntryPoint authenticationDevEntryPoint() {
    return new LoginUrlAuthenticationEntryPoint(ssoLoginPath);
  }
}