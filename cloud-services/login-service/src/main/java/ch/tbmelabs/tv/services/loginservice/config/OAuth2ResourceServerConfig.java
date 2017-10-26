package ch.tbmelabs.tv.services.loginservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.tbmelabs.tv.services.loginservice.filter.OAuth2UsernamePasswordFilter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
  private static final String LOGIN_PROCESSING_URL = "/";

  @Autowired
  private OAuth2UsernamePasswordFilter oauth2UsernamePasswordFilter;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // Add OAuth2 authentication processing filter
    http.addFilterBefore(oauth2UsernamePasswordFilter, UsernamePasswordAuthenticationFilter.class)

        // Disable sessions
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Ignore already authenticated request
        .and().authorizeRequests().anyRequest().anonymous().anyRequest().permitAll()

        // Configure login endpoint
        .and().formLogin().loginProcessingUrl(LOGIN_PROCESSING_URL);
  }
}