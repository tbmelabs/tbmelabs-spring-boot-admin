package ch.tbmelabs.tv.core.servicediscovery.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private Environment environment;

  @Override
  @Profile("dev")
  public void configure(WebSecurity web) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http

        .authorizeRequests().anyRequest().anonymous();
  }
}