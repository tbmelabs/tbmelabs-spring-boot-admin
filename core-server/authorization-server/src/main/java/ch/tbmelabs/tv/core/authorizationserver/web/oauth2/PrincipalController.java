package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;

@RestController
public class PrincipalController {
  private static final Logger LOGGER = LogManager.getLogger(PrincipalController.class);

  @Autowired
  private UserCRUDRepository userRepository;

  @RequestMapping({ "/me", "/user" })
  public Map<String, String> getPrincipal(Principal principal) {
    LOGGER.debug("Getting user information for \"" + principal.getName() + "\"");

    User user = userRepository.findByUsername(principal.getName());

    Map<String, String> userInformation = new HashMap<>();

    userInformation.put("login", user.getUsername());
    userInformation.put("email", user.getEmail());

    return userInformation;
  }
}