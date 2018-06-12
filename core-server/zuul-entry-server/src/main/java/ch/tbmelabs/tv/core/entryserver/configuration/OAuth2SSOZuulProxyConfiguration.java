package ch.tbmelabs.tv.core.entryserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@EnableZuulProxy
@EnableOAuth2Sso
public class OAuth2SSOZuulProxyConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment environment;

  @Override
  public void configure(WebSecurity web) throws Exception {
    if (environment.acceptsProfiles(SpringApplicationProfile.DEV)) {
      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
    
      .authorizeRequests()
        .antMatchers("/", "/favicon.ico").permitAll()
        .antMatchers("/public/**").permitAll()
        .anyRequest().authenticated()

      .and().logout()
        .logoutSuccessUrl("/");
    // @formatter:on
  }
}
