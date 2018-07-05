package ch.tbmelabs.tv.core.adminserver.configuration;

import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@EnableAdminServer
public class OAuth2SSOAdminServerConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      
      .authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
        .anyRequest().hasAnyAuthority(UserAuthority.GANDALF, UserAuthority.SERVER_ADMIN, UserAuthority.SERVER_SUPPORT)

      .and().exceptionHandling()
        .accessDeniedPage("/403.html");
    // @formatter:on
  }
}
