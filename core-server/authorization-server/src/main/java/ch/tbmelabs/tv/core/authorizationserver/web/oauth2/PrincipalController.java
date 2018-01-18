package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {
  @RequestMapping({ "/me", "/user" })
  public Map<String, String> getPrincipal(Principal principal) {
    Map<String, String> userInformation = new HashMap<>();

    userInformation.put("login", principal.getName());

    return userInformation;
  }
}