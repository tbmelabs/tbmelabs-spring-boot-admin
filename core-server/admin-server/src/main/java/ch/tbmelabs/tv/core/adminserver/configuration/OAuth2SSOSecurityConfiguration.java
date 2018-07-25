package ch.tbmelabs.tv.core.adminserver.configuration;

import ch.tbmelabs.tv.shared.constants.security.UserRole;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SSOSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      
      .antMatcher("/**").authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
        .anyRequest().hasAnyAuthority(UserRole.GANDALF, UserRole.SERVER_ADMIN, UserRole.SERVER_SUPPORT)
      
      .and().exceptionHandling()
        .accessDeniedPage("/403.html");
    // @formatter:on
  }
}
