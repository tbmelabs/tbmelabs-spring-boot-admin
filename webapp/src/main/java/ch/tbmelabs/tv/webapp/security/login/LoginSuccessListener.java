package ch.tbmelabs.tv.webapp.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.webapp.model.AuthenticationLog;
import ch.tbmelabs.tv.webapp.model.repository.AccountCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.AuthenticationLogCRUDRepository;

@Component
public class LoginSuccessListener extends SimpleUrlAuthenticationSuccessHandler {
  @Autowired
  private AccountCRUDRepository accountRepository;

  @Autowired
  private AuthenticationLogCRUDRepository logRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    // To return session id (saved in react)
    clearAuthenticationAttributes(request);
    getRedirectStrategy().sendRedirect(request, response, "/login/token");

    logRepository.save(new AuthenticationLog(AuthenticationStatus.AUTHENTICATION_SUCCEED, request.getRemoteAddr(), null,
        accountRepository.findByUsername(authentication.getName())));
  }
}