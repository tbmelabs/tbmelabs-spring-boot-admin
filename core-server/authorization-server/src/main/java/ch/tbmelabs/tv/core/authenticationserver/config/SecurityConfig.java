package ch.tbmelabs.tv.core.authenticationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.core.authenticationserver.security.login.OAuth2LoginFailureHandler;
import ch.tbmelabs.tv.core.authenticationserver.security.login.OAuth2LoginSuccessHandler;
import ch.tbmelabs.tv.core.authenticationserver.security.login.OAuth2LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  @Profile("dev")
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        
        .csrf().disable()
    
        .authorizeRequests().antMatchers("/", "/public/**", "/signup/**").permitAll()

        .and().authorizeRequests().anyRequest().authenticated()
        
        .and().formLogin().loginProcessingUrl("/")
        .failureHandler(new OAuth2LoginFailureHandler())
        .successHandler(new OAuth2LoginSuccessHandler())
        .and().exceptionHandling().authenticationEntryPoint(new OAuth2LoginUrlAuthenticationEntryPoint("/"));
    // @formatter:on
  }
}