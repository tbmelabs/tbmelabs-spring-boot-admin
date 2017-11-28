package ch.tbmelabs.tv.core.authenticationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authenticationserver.service.ZuulProxiedApplicationTokenService;
import ch.tbmelabs.tv.core.authenticationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authenticationserver.service.userdetails.PreAuthenticatedAuthenticationProviderImpl;
import ch.tbmelabs.tv.core.authenticationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Autowired
  private ObjectPostProcessor<Object> objectPostProcessor;

  @Autowired
  private PreAuthenticatedAuthenticationProviderImpl preAuthenticationProvider;

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.authenticationProvider(preAuthenticationProvider).userDetailsService(userDetailsService)
        .passwordEncoder(User.PASSWORD_ENCODER);
    return builder.build();
  }

  @Bean
  public ZuulProxiedApplicationTokenService tokenServiceBean() throws Exception {
    ZuulProxiedApplicationTokenService tokenService = new ZuulProxiedApplicationTokenService();
    tokenService.setTokenStore(tokenStore);
    tokenService.setClientDetailsService(clientDetailsService);
    tokenService.setAuthenticationManager(authenticationManagerBean());
    return tokenService;
  }

  @Bean
  public WebResponseExceptionTranslator loggingExceptionTranslator() {
    return new DefaultWebResponseExceptionTranslator() {
      @Override
      public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        e.printStackTrace();

        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(responseEntity.getHeaders().toSingleValueMap());
        OAuth2Exception excBody = responseEntity.getBody();
        return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
      }
    };
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenServices(tokenServiceBean()).tokenStore(tokenStore)
        .authenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService)
        .exceptionTranslator(loggingExceptionTranslator());
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