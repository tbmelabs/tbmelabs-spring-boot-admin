package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

import ch.tbmelabs.tv.core.authorizationserver.security.csrf.CsrfHeaderFilter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private Environment environment;

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains(SpringApplicationProfile.DEV)) {
      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      
      .authorizeRequests()
        .antMatchers("/","/public/**").permitAll()
        .antMatchers("/signup/**").permitAll()
        .antMatchers("/me","/user").permitAll()
      .anyRequest().authenticated()
      
      .and().formLogin().loginPage("/").loginProcessingUrl("/signin")
      
      .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    // @formatter:on
  }
}