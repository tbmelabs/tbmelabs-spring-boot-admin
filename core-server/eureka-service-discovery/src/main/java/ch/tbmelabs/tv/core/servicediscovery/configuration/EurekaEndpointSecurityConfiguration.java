package ch.tbmelabs.tv.core.servicediscovery.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Configuration
@EnableWebSecurity
public class EurekaEndpointSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

        .csrf().ignoringAntMatchers("/eureka/**")

        .and().antMatcher("/eureka/**").authorizeRequests()
          .antMatchers("/eureka/**").hasRole("CLIENT")

        .and().httpBasic();
    // @formatter:on
  }
}
