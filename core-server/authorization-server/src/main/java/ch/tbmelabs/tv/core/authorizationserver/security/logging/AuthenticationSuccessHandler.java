package ch.tbmelabs.tv.core.authorizationserver.security.logging;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private static final String NO_REDIRECT_HEADER = "no-redirect";
  private static final String X_FORWARDED_HEADER = "X-FORWARDED-FOR";
  private static final String USERNAME_PARAMETER = "username";

  private static String noSavedRequestExceptionIdMessage = UUID.randomUUID().toString();

  private AuthenticationAttemptLogger authenticationLogger;

  public AuthenticationSuccessHandler(AuthenticationAttemptLogger authenticationAttemptLogger) {
    this.authenticationLogger = authenticationAttemptLogger;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    try {
      String savedRedirectUrl;
      if (request.getHeader(NO_REDIRECT_HEADER) != null
          && (savedRedirectUrl = getSavedRequestCacheRedirectUrl(request, response)) != null) {
        logger.debug("Header \"" + NO_REDIRECT_HEADER + "\" is present: Not sending redirect");
        response.setHeader(NO_REDIRECT_HEADER, savedRedirectUrl);
      } else {
        throw new IllegalArgumentException(noSavedRequestExceptionIdMessage);
      }
    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
        | SecurityException e) {
      if (IllegalArgumentException.class.isAssignableFrom(e.getClass())
          && !e.getLocalizedMessage().equals(noSavedRequestExceptionIdMessage)) {
        throw (IllegalArgumentException) e;
      }

      super.onAuthenticationSuccess(request, response, authentication);
    }

    String requestIp = request.getHeader(X_FORWARDED_HEADER);
    if (requestIp == null) {
      requestIp = request.getRemoteAddr();
    }

    logger.debug("Successfull authentication from " + requestIp);

    authenticationLogger.logAuthenticationAttempt(AUTHENTICATION_STATE.OK, requestIp, null,
        request.getParameter(USERNAME_PARAMETER));
  }

  private String getSavedRequestCacheRedirectUrl(HttpServletRequest request,
      HttpServletResponse response) throws IllegalAccessException, NoSuchFieldException {
    Field requestCache =
        AuthenticationSuccessHandler.class.getSuperclass().getDeclaredField("requestCache");
    requestCache.setAccessible(true);

    SavedRequest savedRequest;
    if ((savedRequest =
        ((RequestCache) requestCache.get(this)).getRequest(request, response)) != null) {
      return savedRequest.getRedirectUrl();
    }

    return null;
  }
}
