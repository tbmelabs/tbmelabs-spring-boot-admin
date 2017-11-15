package ch.tbmelabs.tv.core.security.authentication;

import java.net.MalformedURLException;
import java.net.URL;

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
  public String getLoginFormUrl() {
    try {
      return new URL(super.getLoginFormUrl()).getPath();
    } catch (MalformedURLException e) {
      return super.getLoginFormUrl();
    }
  }

  @Override
  protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) {
    return super.determineUrlToUseForThisRequest(request, response, exception) + loginSuccessRedirectArguments(request);
  }

  private String loginSuccessRedirectArguments(HttpServletRequest request) {
    StringBuilder redirectUrl = new StringBuilder();

    redirectUrl.append(ARGUMENT_PREFIX).append(REDIRECT_ARGUMENT_NAME).append(ARGUMENT_BINDER)
        .append(request.getRequestURL());

    if (request.getQueryString() != null) {
      redirectUrl.append(ARGUMENT_SEPARATOR).append(request.getQueryString());
    }

    return redirectUrl.toString();
  }
}