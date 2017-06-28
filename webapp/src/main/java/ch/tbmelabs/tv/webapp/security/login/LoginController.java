package ch.tbmelabs.tv.webapp.security.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
  @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
  public void redirectToReactLogin(HttpServletResponse response) throws IOException {
    response.sendRedirect("/#/login");
  }

  @RequestMapping(value = { "/login/token" }, method = RequestMethod.GET)
  @ResponseBody
  public String getSessionToken(HttpServletRequest request) {
    return request.getSession().getId();
  }

  @RequestMapping(value = { "/login/token/not-expired" }, method = RequestMethod.GET)
  @ResponseBody
  public Boolean checkSessionNotExpired(HttpServletRequest request) {
    return request.getSession(false) != null;
  }
}