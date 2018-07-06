package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.RoleDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.RoleMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailServiceImpl;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Service
public class UserSignupServiceImpl implements UserSignupService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserSignupServiceImpl.class);

  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,20}$";
  private static final String PASSWORD_REGEX =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  private ApplicationContext applicationContext;

  private UserCRUDRepository userRepository;

  private RoleCRUDRepository roleRepository;

  private RoleMapper roleMapper;

  private UserService userService;

  private UserMapper userMapper;

  public UserSignupServiceImpl(ApplicationContext applicationContext,
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
    LOGGER.debug("Checking if username \'{}\' is unique", testUser.getUsername());

    return !userRepository.findOneByUsernameIgnoreCase(testUser.getUsername()).isPresent();
  }

  public boolean doesUsernameMatchFormat(UserDTO testUser) {
    LOGGER.debug("Checking if username \'{}\' does match format", testUser.getUsername());

    return testUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(UserDTO testUser) {
    LOGGER.debug("Checking if email \'{}\' is unique", testUser.getEmail());

    return !userRepository.findOneByEmailIgnoreCase(testUser.getEmail()).isPresent();
  }

  public boolean isEmailAddress(UserDTO testUser) {
    LOGGER.debug("Checking if email \'{}\' does match format", testUser.getEmail());

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

    LOGGER.info("New user signed up! username: {}; email: {}", newUserDTO.getUsername(),
        newUserDTO.getEmail());

    sendConfirmationEmailIfEmailIsEnabled(persistedUser);

    return userMapper.toDto(persistedUser);
  }

  public boolean isUserValid(UserDTO testUser) {
    LOGGER.debug("Checking if user \'{}\' is valid", testUser);

    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser)
        && isEmailAddressUnique(testUser) && isEmailAddress(testUser)
        && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
  }

  public UserDTO setDefaultRolesIfNonePresent(UserDTO newUserDTO) {
    LOGGER.debug("Setting default roles for {}", newUserDTO.getUsername());

    if (newUserDTO.getRoles() == null || newUserDTO.getRoles().isEmpty()) {
      Optional<Role> userRole;
      if (!(userRole = roleRepository.findOneByName(UserAuthority.USER)).isPresent()) {
        throw new IllegalArgumentException(
            "Unable to find default authority \'" + UserAuthority.USER + "\'!");
      }

      newUserDTO.setRoles(
          new HashSet<RoleDTO>(Collections.singletonList(roleMapper.toDto(userRole.get()))));
    }

    return newUserDTO;
  }

  public User sendConfirmationEmailIfEmailIsEnabled(User persistedUser) {
    final Environment environment = applicationContext.getEnvironment();

    if (!environment.acceptsProfiles(SpringApplicationProfile.NO_MAIL)) {
      applicationContext.getBean(UserMailServiceImpl.class).sendSignupConfirmation(persistedUser);
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
