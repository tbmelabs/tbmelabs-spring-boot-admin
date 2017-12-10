package ch.tbmelabs.tv.core.authorizationserver.security.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class OAuth2LoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
  public OAuth2LoginUrlAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) {
    return super.buildRedirectUrlToLoginPage(request, response, authException) + "?" + request.getQueryString();
  }
}