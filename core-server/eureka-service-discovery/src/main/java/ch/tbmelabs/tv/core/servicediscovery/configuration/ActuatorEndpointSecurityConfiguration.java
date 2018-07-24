package ch.tbmelabs.tv.core.servicediscovery.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ch.tbmelabs.tv.shared.constants.security.ApplicationUserRole;

@Order(2)
@Configuration
@EnableWebSecurity
public class ActuatorEndpointSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  public ActuatorEndpointSecurityConfiguration(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected AuthenticationManager authenticationManager() {
    return authenticationManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

        .csrf().ignoringAntMatchers("/eureka/**")

        .and().antMatcher("/actuator/**").authorizeRequests()
          .antMatchers("/actuator/**").hasRole(ApplicationUserRole.ACTUATOR_ROLE)

        .and().httpBasic();
    // @formatter:on
  }
}
