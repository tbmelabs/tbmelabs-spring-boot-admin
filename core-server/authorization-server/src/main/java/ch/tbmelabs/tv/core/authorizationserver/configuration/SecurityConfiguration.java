package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.core.authorizationserver.security.filter.OAuth2BearerTokenAuthenticationFilter;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationFailureHandler;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  private AuthenticationFailureHandler authenticationFailureHandler;

  private AuthenticationSuccessHandler authenticationSuccessHandler;

  private OAuth2BearerTokenAuthenticationFilter oAuth2AuthenticationFilter;

  public SecurityConfiguration(AuthenticationManager authenticationManager,
      AuthenticationFailureHandler authenticationFailureHandler,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      OAuth2BearerTokenAuthenticationFilter oAuth2AuthenticationFilter) {
    this.authenticationManager = authenticationManager;
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.oAuth2AuthenticationFilter = oAuth2AuthenticationFilter;
  }

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

      .authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/me","/user").permitAll()
        .antMatchers("/signup/**").permitAll()
        .antMatchers("/public/**").permitAll()
      .anyRequest().authenticated()

      .and().formLogin()
        .failureHandler(authenticationFailureHandler)
        .successHandler(authenticationSuccessHandler)
      .and().httpBasic()

      .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()

      .and()
        .addFilterBefore(oAuth2AuthenticationFilter, BasicAuthenticationFilter.class);
    // @formatter:on
  }
}
