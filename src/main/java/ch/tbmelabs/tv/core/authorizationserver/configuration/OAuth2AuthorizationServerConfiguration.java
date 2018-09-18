package ch.tbmelabs.tv.core.authorizationserver.configuration;

import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  private ClientDetailsServiceImpl clientDetailsService;

  private DefaultTokenServices tokenService;

  private LoggingExceptionTranslator loggingExceptionTranslator;

  private TokenStore tokenStore;

  private UserDetailsServiceImpl userDetailsService;

  public OAuth2AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
    ClientDetailsServiceImpl clientDetailsService, DefaultTokenServices defaultTokenServices,
    LoggingExceptionTranslator loggingExceptionTranslator, TokenStore tokenStore,
    UserDetailsServiceImpl userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    this.tokenService = defaultTokenServices;
    this.loggingExceptionTranslator = loggingExceptionTranslator;
    this.tokenStore = tokenStore;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(authenticationManager)
      .exceptionTranslator(loggingExceptionTranslator).reuseRefreshTokens(false)
      .tokenServices(tokenService).tokenStore(tokenStore).userDetailsService(userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }
}
