package ch.tbmelabs.tv.core.entryserver.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SSOSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http

      .authorizeRequests()
        .antMatchers("/", "/favicon.ico").permitAll()
        .antMatchers("/public/**").permitAll()
        .antMatchers("/authenticated").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated()

      .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
        .logoutSuccessUrl("/");
    // @formatter:on
  }
}
