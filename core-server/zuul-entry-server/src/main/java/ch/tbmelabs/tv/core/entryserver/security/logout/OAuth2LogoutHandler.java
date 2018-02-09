package ch.tbmelabs.tv.core.entryserver.security.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LogoutHandler extends SimpleUrlLogoutSuccessHandler {
  private static final String NO_REDIRECT_HEADER = "no-redirect";
  private static final String GOODBYE_ENDPOINT_URI = "/logout";

  @Value("${OAUTH2_SERVER_BASE_URI}")
  private String oauth2ServerUri;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    if (request.getHeader(NO_REDIRECT_HEADER) != null) {
      response.setHeader(NO_REDIRECT_HEADER, oauth2ServerUri + GOODBYE_ENDPOINT_URI);
    } else {
      super.onLogoutSuccess(request, response, authentication);
    }
  }
}