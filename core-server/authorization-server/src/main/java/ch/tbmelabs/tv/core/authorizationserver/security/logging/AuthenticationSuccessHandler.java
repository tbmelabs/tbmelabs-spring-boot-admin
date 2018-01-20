package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationSuccessHandler.class);

  private static final String X_FORWARDED_HEADER = "X-FORWARDED-FOR";
  private static final String USERNAME_PARAMETER = "username";

  @Autowired
  private AuthenticationAttemptLogger authenticationLogger;

  @Autowired
  private BruteforceFilterService bruteforceFilter;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    super.onAuthenticationSuccess(request, response, authentication);

    LOGGER.debug("Detected successful authentication");

    String requestIp = request.getHeader(X_FORWARDED_HEADER);
    if (requestIp == null) {
      requestIp = request.getRemoteAddr();
    }

    authenticationLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.OK, requestIp, null,
        request.getParameter(USERNAME_PARAMETER));

    bruteforceFilter.authenticationFromIpSucceed(requestIp);
  }
}