package ch.tbmelabs.tv.core.entryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.tbmelabs.tv.core.entryserver.security.filter.OAuth2RefreshTokenAuthenticationFilter;
import ch.tbmelabs.tv.core.entryserver.security.filter.TemporaryZuulAuthenticationFilter;

@Configuration
@EnableZuulProxy
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SsoZuulProxyConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private TemporaryZuulAuthenticationFilter zuulAuthenticationFilter;

  @Autowired
  private OAuth2RefreshTokenAuthenticationFilter refreshTokenFilter;

  @Override
  @Profile("dev")
  public void configure(WebSecurity web) throws Exception {
    web.debug(true);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()

        .addFilterBefore(zuulAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(refreshTokenFilter, TemporaryZuulAuthenticationFilter.class)

        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated();
  }
}