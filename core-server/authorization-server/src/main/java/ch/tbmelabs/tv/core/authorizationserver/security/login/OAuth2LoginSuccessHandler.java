package ch.tbmelabs.tv.core.authorizationserver.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private static final String X_FORWARDED_HEADER = "X-FORWARDED-FOR";
  private static final String USERNAME_PARAMETER = "username";
  private static final String REDIRECT_PROPERTY_NAME = "authorization-server.security.login.default-redirect";

  private String defaultRedirectUrl;

  @Autowired
  private AuthenticationAttemptLogger authenticationLogger;

  @Autowired
  private BruteforceFilterService bruteforceFilter;

  public OAuth2LoginSuccessHandler() {
    this.defaultRedirectUrl = ApplicationContextHolder.getApplicationContext().getEnvironment()
        .getProperty(REDIRECT_PROPERTY_NAME);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String requestIp = request.getHeader(X_FORWARDED_HEADER);
    if (requestIp == null) {
      requestIp = request.getRemoteAddr();
    }

    authenticationLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.OK, requestIp, null,
        ((HttpServletRequest) request).getParameter(USERNAME_PARAMETER));

    bruteforceFilter.authenticationFromIpSucceed(requestIp);

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