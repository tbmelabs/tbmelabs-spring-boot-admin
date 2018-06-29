package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.RoleMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailService;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserSignupService {

  private static final Logger LOGGER = LogManager.getLogger(UserSignupService.class);

  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,20}$";
  private static final String PASSWORD_REGEX =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  private ApplicationContext applicationContext;

  private UserCRUDRepository userRepository;

  private RoleCRUDRepository roleRepository;

  private RoleMapper roleMapper;

  private UserService userService;

  private UserMapper userMapper;

  public UserSignupService(ApplicationContext applicationContext,
      UserCRUDRepository userCRUDRepository, RoleCRUDRepository roleCRUDRepository,
      RoleMapper roleMapper, UserService userService, UserMapper userMapper) {
    this.applicationContext = applicationContext;
    this.userRepository = userCRUDRepository;
    this.roleRepository = roleCRUDRepository;
    this.roleMapper = roleMapper;
    this.userService = userService;
    this.userMapper = userMapper;
  }

  public boolean isUsernameUnique(UserDTO testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" is unique");

    return !userRepository.findOneByUsernameIgnoreCase(testUser.getUsername()).isPresent();
  }

  public boolean doesUsernameMatchFormat(UserDTO testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" does match format");

    return testUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(UserDTO testUser) {
    LOGGER.debug("Checking if email \"" + testUser.getEmail() + "\" is unique");

    return !userRepository.findOneByEmailIgnoreCase(testUser.getEmail()).isPresent();
  }

  public boolean isEmailAddress(UserDTO testUser) {
    LOGGER.debug("Checking if email \"" + testUser.getEmail() + "\" does match format");

    return EmailValidator.getInstance().isValid(testUser.getEmail());
  }

  public boolean doesPasswordMatchFormat(UserDTO testUser) {
    LOGGER.debug("Checking password format for new user");

    return testUser.getPassword().matches(PASSWORD_REGEX);
  }

  public boolean doPasswordsMatch(UserDTO testUser) {
    LOGGER.debug("Checking password match for new user");

    return testUser.getConfirmation().equals(testUser.getPassword());
  }

  public UserDTO signUpNewUser(UserDTO newUserDTO) {
    if (!isUserValid(newUserDTO)) {
      throw new IllegalArgumentException("An error occured. Please check your details!");
    }

    newUserDTO = setDefaultRolesIfNonePresent(newUserDTO);
    User persistedUser = userService.save(newUserDTO);

    LOGGER.info("New user signed up! username: " + newUserDTO.getUsername() + "; email: "
        + newUserDTO.getEmail());

    sendConfirmationEmailIfEmailIsEnabled(persistedUser);

    return userMapper.toDto(persistedUser);
  }

  private boolean isUserValid(UserDTO testUser) {
    LOGGER.debug("Checking if user \"" + testUser + "\" is valid");

    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser)
        && isEmailAddressUnique(testUser) && isEmailAddress(testUser)
        && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
  }

  private UserDTO setDefaultRolesIfNonePresent(UserDTO newUserDTO) {
    LOGGER.debug("Setting default roles for " + newUserDTO.getUsername());

    if (newUserDTO.getRoles() == null || newUserDTO.getRoles().isEmpty()) {
      Optional<Role> userRole;
      if (!(userRole = roleRepository.findOneByName(UserAuthority.USER)).isPresent()) {
        throw new IllegalArgumentException(
            "Unable to find default authority \"" + UserAuthority.USER + "\"!");
      }

      newUserDTO.setRoles(
          new HashSet<RoleDTO>(Collections.singletonList(roleMapper.toDto(userRole.get()))));
    }

    return newUserDTO;
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
      userRepository.updateUserSetIsEnabledTrue(persistedUser);
    }

    return persistedUser;
  }
}
