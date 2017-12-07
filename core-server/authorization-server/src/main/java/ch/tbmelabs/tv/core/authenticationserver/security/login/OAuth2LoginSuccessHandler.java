package ch.tbmelabs.tv.core.authenticationserver.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    // TODO: Map error to an error page (not plain to frontend)
    // error="invalid_grant", error_description="Invalid redirect:
    // http://tbme-tv.ch/login does not match one of the registered values:
    // [http://localhost/login]"
    if (!request.getQueryString().isEmpty()) {
      response.sendRedirect("/oauth/authorize?" + request.getQueryString());
    }
  }
}