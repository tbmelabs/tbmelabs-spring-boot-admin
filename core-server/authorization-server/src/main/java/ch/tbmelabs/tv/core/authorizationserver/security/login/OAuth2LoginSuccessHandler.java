package ch.tbmelabs.tv.core.authorizationserver.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;

public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private static final String REDIRECT_PROPERTY_NAME = "authorization-server.security.login.default-redirect";

  private String defaultRedirectUrl;

  public OAuth2LoginSuccessHandler() {
    this.defaultRedirectUrl = ApplicationContextHolder.getApplicationContext().getEnvironment()
        .getProperty(REDIRECT_PROPERTY_NAME);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // TODO: Map error to an error page (not plain to frontend)
    // error="invalid_grant", error_description="Invalid redirect:
    // http://tbme-tv.ch/login does not match one of the registered values:
    // [http://localhost/login]"
    if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
      response.sendRedirect(defaultRedirectUrl);
    } else {
      response.sendRedirect("oauth/authorize?" + request.getQueryString());
    }
  }
}