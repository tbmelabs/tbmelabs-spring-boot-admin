package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;

@RestController
public class PrincipalController {
  @RequestMapping({ "/me", "/user" })
  public User getPrincipal(User user) {
    return user;
  }
}