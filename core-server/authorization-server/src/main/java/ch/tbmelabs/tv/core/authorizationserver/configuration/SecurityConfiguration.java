package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.core.authorizationserver.security.filter.BlacklistedIpFilter;
import ch.tbmelabs.tv.core.authorizationserver.security.filter.OAuth2BearerTokenAuthenticationFilter;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationFailureHandler;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationSuccessHandler;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final Logger LOGGER = LogManager.getLogger(SecurityConfiguration.class);

  private AuthenticationManager authenticationManager;

  private Environment environment;

  private AuthenticationFailureHandler authenticationFailureHandler;

  private AuthenticationSuccessHandler authenticationSuccessHandler;

  private OAuth2BearerTokenAuthenticationFilter oAuth2AuthenticationFilter;

  private BlacklistedIpFilter blacklistedIpFilter;

  public SecurityConfiguration(AuthenticationManager authenticationManager, Environment environment,
      AuthenticationFailureHandler authenticationFailureHandler,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      OAuth2BearerTokenAuthenticationFilter oAuth2AuthenticationFilter,
      BlacklistedIpFilter blacklistedIpFilter) {
    this.authenticationManager = authenticationManager;
    this.environment = environment;
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.oAuth2AuthenticationFilter = oAuth2AuthenticationFilter;
    this.blacklistedIpFilter = blacklistedIpFilter;
  }

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    if (environment.acceptsProfiles(SpringApplicationProfile.DEV)) {
      LOGGER.warn("Profile \"" + SpringApplicationProfile.DEV
          + "\" is active: Web request debugging is enabled!");

      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

      .authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/me","/user").permitAll()
        .antMatchers("/signup").permitAll()
        .antMatchers("/public/**").permitAll()
      .anyRequest().authenticated()

      .and().formLogin()
        .failureHandler(authenticationFailureHandler)
        .successHandler(authenticationSuccessHandler)
      .and().httpBasic()

      .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()

      .and()
        .addFilterBefore(oAuth2AuthenticationFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(blacklistedIpFilter, UsernamePasswordAuthenticationFilter.class);
    // @formatter:on
  }
}
