package ch.tbmelabs.tv.core.servicediscovery.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SSOSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

      .csrf().ignoringAntMatchers("/eureka/**")

      .and().authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/eureka/**").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
        .anyRequest().hasAnyAuthority(UserAuthority.GANDALF, UserAuthority.SERVER_ADMIN, UserAuthority.SERVER_SUPPORT)

      .and().exceptionHandling()
        .accessDeniedPage("/403.html");
    // @formatter:on
  }
}
