package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.RoleMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailService;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import java.util.Collections;
import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserSignupService {

  private static final Logger LOGGER = LogManager.getLogger(UserSignupService.class);

  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,64}";
  private static final String PASSWORD_REGEX =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private RoleMapper roleMapper;

  public boolean isUsernameUnique(User testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" is unique");

    return !userRepository.findOneByUsernameIgnoreCase(testUser.getUsername()).isPresent();
  }

  public boolean doesUsernameMatchFormat(User testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" does match format");

    return testUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(User testUser) {
    LOGGER.debug("Checking if email \"" + testUser.getEmail() + "\" is unique");

    return !userRepository.findOneByEmailIgnoreCase(testUser.getEmail()).isPresent();
  }

  public boolean isEmailAddress(User testUser) {
    LOGGER.debug("Checking if email \"" + testUser.getEmail() + "\" does match format");

    return EmailValidator.getInstance().isValid(testUser.getEmail());
  }

  public boolean doesPasswordMatchFormat(User testUser) {
    LOGGER.debug("Checking password format for new user");

    return testUser.getPassword().matches(PASSWORD_REGEX);
  }

  public boolean doPasswordsMatch(User testUser) {
    LOGGER.debug("Checking password match for new user");

    return testUser.getConfirmation().equals(testUser.getPassword());
  }

  public User signUpNewUser(User newUser) {
    if (!isUserValid(newUser)) {
      throw new IllegalArgumentException("An error occured. Please check your details!");
    }

    LOGGER.info("New user signed up! username: " + newUser.getUsername() + "; email: "
        + newUser.getEmail());

    User persistedUser = userRepository.save(newUser);
    setDefaultRolesIfNonePresent(newUser);
    userRepository.save(persistedUser);

    sendConfirmationEmailIfEmailIsEnabled(persistedUser);

    return persistedUser;
  }

  private boolean isUserValid(User testUser) {
    LOGGER.debug("Checking if user \"" + testUser + "\" is valid");

    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser)
        && isEmailAddressUnique(testUser) && isEmailAddress(testUser)
        && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
  }

  private User setDefaultRolesIfNonePresent(User newUser) {
    LOGGER.debug("Setting default roles to " + newUser.getUsername());

    if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
      Optional<Role> userRole;
      if (!(userRole = roleRepository.findOneByName(UserAuthority.USER)).isPresent()) {
        throw new IllegalArgumentException(
            "Unable to find default authority \"" + UserAuthority.USER + "\"!");
      }

      newUser.setRoles(
          userMapper.rolesToAssociations(
              Collections.singletonList(roleMapper.toDto(userRole.get())), newUser));
    }

    return newUser;
  }

  private User sendConfirmationEmailIfEmailIsEnabled(User persistedUser) {
    final Environment environment = applicationContext.getEnvironment();

    if (!environment.acceptsProfiles(SpringApplicationProfile.NO_MAIL)) {
      applicationContext.getBean(UserMailService.class).sendSignupConfirmation(persistedUser);
    } else if (!environment.acceptsProfiles(SpringApplicationProfile.DEV,
        SpringApplicationProfile.TEST)) {
      throw new IllegalArgumentException(
          "You cannot run a productive environment without any mail configuration!");
    } else {
      persistedUser.setIsEnabled(true);
    }

    return userRepository.save(persistedUser);
  }
}
