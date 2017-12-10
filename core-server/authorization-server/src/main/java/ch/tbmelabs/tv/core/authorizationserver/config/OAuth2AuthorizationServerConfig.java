package ch.tbmelabs.tv.core.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.service.ZuulProxiedApplicationTokenService;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;

@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Autowired
  private LoggingExceptionTranslator loggingExceptionTranslator;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Bean
  public ZuulProxiedApplicationTokenService tokenServiceBean() throws Exception {
    ZuulProxiedApplicationTokenService tokenService = new ZuulProxiedApplicationTokenService();
    tokenService.setTokenStore(tokenStore);
    tokenService.setClientDetailsService(clientDetailsService);
    tokenService.setAuthenticationManager(authenticationManager);
    return tokenService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenServices(tokenServiceBean()).tokenStore(tokenStore).reuseRefreshTokens(false)
        .authenticationManager(authenticationManager).userDetailsService(userDetailsService)
        .exceptionTranslator(loggingExceptionTranslator);
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