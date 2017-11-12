package ch.tbmelabs.tv.core.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AdvancedLoginUrlAuthenticationPoint extends LoginUrlAuthenticationEntryPoint {
  private static final String ARGUMENT_PREFIX = "?";
  private static final String ARGUMENT_BINDER = "=";
  private static final String ARGUMENT_SEPARATOR = "&";
  private static final String REDIRECT_ARGUMENT_NAME = "redirect";

  public AdvancedLoginUrlAuthenticationPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) {
    return super.determineUrlToUseForThisRequest(request, response, exception) + loginSuccessRedirectArguments(request);
  }

  private String loginSuccessRedirectArguments(HttpServletRequest request) {
    return ARGUMENT_PREFIX + REDIRECT_ARGUMENT_NAME + ARGUMENT_BINDER + request.getRequestURI() + ARGUMENT_SEPARATOR
        + request.getQueryString();
  }
}