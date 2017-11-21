package ch.tbmelabs.tv.core.entryserver.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import ch.tbmelabs.tv.core.entryserver.service.RefreshTokenCheckService;
import ch.tbmelabs.tv.shared.domain.authentication.user.Role;
import ch.tbmelabs.tv.shared.domain.authentication.user.Role.DefaultRole;

@Component
public class OAuth2RefreshTokenAuthenticationFilter extends GenericFilterBean {
  private static final Logger LOGGER = LogManager.getLogger(OAuth2RefreshTokenAuthenticationFilter.class);

  private static final String REFRESH_TOKEN_QUERY = "refresh_token";

  @Autowired
  private RefreshTokenCheckService tokenChecker;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (applyFilter(request)
        && tokenChecker.authenticateWithRefreshToken(request.getParameter(REFRESH_TOKEN_QUERY), response)) {
      LOGGER.info("Request '" + request.getQueryString() + "' matched " + OAuth2RefreshTokenAuthenticationFilter.class);

      SecurityContextHolder.getContext().setAuthentication(temporaryZuulAuthentication());
    }

    chain.doFilter(req, res);
  }

  private boolean applyFilter(HttpServletRequest request) {
    LOGGER.debug("Checking match of query '" + request.getQueryString() + "' against "
        + OAuth2RefreshTokenAuthenticationFilter.class);

    return request.getParameter(REFRESH_TOKEN_QUERY) != null;
  }

  private Authentication temporaryZuulAuthentication() {
    return new Authentication() {
      private static final long serialVersionUID = 1L;

      @Override
      public String getName() {
        return DefaultRole.TMP_ZUUL_USER.toString();
      }

      @Override
      public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        LOGGER.debug("Nice try dude!");
      }

      @Override
      public boolean isAuthenticated() {
        return true;
      }

      @Override
      public Object getPrincipal() {
        return new Principal() {
          @Override
          public String getName() {
            return DefaultRole.TMP_ZUUL_USER.toString();
          }
        };
      }

      @Override
      public Object getDetails() {
        return null;
      }

      @Override
      public Object getCredentials() {
        return null;
      }

      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) Arrays.asList(new Role(DefaultRole.TMP_ZUUL_USER.toString()));
      }
    };
  }
}