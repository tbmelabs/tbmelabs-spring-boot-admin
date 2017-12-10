package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  @Profile("dev")
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http

        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        .and().authorizeRequests().anyRequest().authenticated();
  }
}