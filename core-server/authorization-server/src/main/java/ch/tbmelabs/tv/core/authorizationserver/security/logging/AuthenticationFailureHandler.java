package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  private static final Logger LOGGER = LogManager.getLogger(AuthenticationFailureHandler.class);

  private static final String DEFAULT_ERROR_REDIRECT_URL = "/signin?error";
  private static final String X_FORWARDED_HEADER = "X-FORWARDED-FOR";
  private static final String USERNAME_PARAMETER = "username";

  @Autowired
  private AuthenticationAttemptLogger authenticationLogger;

  @Autowired
  private BruteforceFilterService bruteforceFilter;

  @PostConstruct
  public void initBean() {
    LOGGER.info("Initializing..");

    setDefaultFailureUrl(DEFAULT_ERROR_REDIRECT_URL);
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);

    LOGGER.debug("Detected failed authentication");

    String requestIp = request.getHeader(X_FORWARDED_HEADER);
    if (requestIp == null) {
      requestIp = request.getRemoteAddr();
    }

    authenticationLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.NOK, requestIp, exception.getLocalizedMessage(),
        request.getParameter(USERNAME_PARAMETER));

    bruteforceFilter.authenticationFromIpFailed(requestIp);
  }
}