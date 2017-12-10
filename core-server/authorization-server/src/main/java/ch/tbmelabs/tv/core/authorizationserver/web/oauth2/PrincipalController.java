package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {
  @RequestMapping({ "/me", "/user" })
  public Principal getPrincipal(Principal user) {
    return user;
  }
}