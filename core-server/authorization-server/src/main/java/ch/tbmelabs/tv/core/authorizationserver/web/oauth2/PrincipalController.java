package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

  private static final Logger LOGGER = LogManager.getLogger(PrincipalController.class);

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @GetMapping({"/me", "/user"})
  public Map<String, String> getPrincipal() {
    LOGGER.debug("Requesting current user information.");

    User user = userRepository.findCurrentUser();

    Map<String, String> userInformation = new HashMap<>();
    userInformation.put("login", user.getUsername());
    userInformation.put("email", user.getEmail());

    return userInformation;
  }

  @GetMapping({"/profile"})
  public UserDTO getProfile() {
    LOGGER.debug("Requesting current user profile.");

    return userMapper.toDto(userRepository.findCurrentUser());
  }
}
