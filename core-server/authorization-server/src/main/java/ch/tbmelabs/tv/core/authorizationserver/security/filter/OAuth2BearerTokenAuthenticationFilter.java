package ch.tbmelabs.tv.core.authorizationserver.security.filter;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class OAuth2BearerTokenAuthenticationFilter extends GenericFilterBean {
  private static final Logger LOGGER = LogManager.getLogger(OAuth2BearerTokenAuthenticationFilter.class);

  private BearerTokenExtractor bearerTokenExtractor;

  @Autowired
  private TokenStore tokenStore;

  @PostConstruct
  public void initBean() {
    LOGGER.info("Initializing..");

    bearerTokenExtractor = new BearerTokenExtractor();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Authentication authentication;

    if ((authentication = bearerTokenExtractor.extract((HttpServletRequest) request)) != null) {
      LOGGER.debug("Bearer token found, authenticating");

      SecurityContextHolder.getContext()
          .setAuthentication(tokenStore.readAuthentication((String) authentication.getPrincipal()));

      LOGGER.debug("Authentication successful, continuing filter chain");
    }

    chain.doFilter(request, response);
  }
}