package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableZuulProxy
@EnableOAuth2Sso
public class SsoZuulProxyConfig extends WebSecurityConfigurerAdapter {
  @Override
  @Profile("dev")
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }
}