package ch.tbmelabs.tv.webapp.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.webapp.model.AuthenticationLog;
import ch.tbmelabs.tv.webapp.model.repository.AccountCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.AuthenticationLogCRUDRepository;

@Component
public class LoginErrorListener extends SimpleUrlAuthenticationFailureHandler {
  @Autowired
  private AccountCRUDRepository accountRepository;

  @Autowired
  private AuthenticationLogCRUDRepository logRepository;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    logRepository.save(new AuthenticationLog(AuthenticationStatus.AUTHENTICATION_FAILED, request.getRemoteAddr(),
        exception.getLocalizedMessage(), accountRepository.findByUsername(request.getParameter("username"))));
  }
}