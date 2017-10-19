package ch.tbmelabs.tv.authenticationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import ch.tbmelabs.tv.authenticationserver.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.authenticationserver.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.resource.authorization.user.User;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private RedisTokenStore tokenStore;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Autowired
  private ObjectPostProcessor<Object> objectPostProcessor;

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.userDetailsService(userDetailsService).passwordEncoder(User.PASSWORD_ENCODER);
    return builder.build();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService)
        .tokenStore(tokenStore);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }
}