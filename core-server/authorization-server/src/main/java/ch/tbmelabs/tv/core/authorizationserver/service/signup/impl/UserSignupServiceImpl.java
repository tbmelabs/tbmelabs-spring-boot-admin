package ch.tbmelabs.tv.core.authorizationserver.service.signup.impl;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.mapper.UserMapper;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.domain.UserService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.impl.UserMailServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserSignupServiceImpl implements UserSignupService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserSignupServiceImpl.class);

  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,20}$";
  private static final String PASSWORD_REGEX =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  private ApplicationContext applicationContext;

  private UserCRUDRepository userRepository;

  private RoleCRUDRepository roleRepository;

  private UserService userService;

  private UserMapper userMapper;

  private EntityManager entityManager;

  public UserSignupServiceImpl(ApplicationContext applicationContext,
      UserCRUDRepository userCRUDRepository, RoleCRUDRepository roleCRUDRepository,
      UserService userService, UserMapper userMapper, EntityManager entityManager) {
    this.applicationContext = applicationContext;
    this.userRepository = userCRUDRepository;
    this.roleRepository = roleCRUDRepository;
    this.userService = userService;
    this.userMapper = userMapper;
    this.entityManager = entityManager;
  }

  public boolean isUsernameUnique(UserDTO testUser) {
    LOGGER.debug("Checking if username \'{}\' is unique", testUser.getUsername());

    return !userRepository.findByUsernameIgnoreCase(testUser.getUsername()).isPresent();
  }

  public boolean doesUsernameMatchFormat(UserDTO testUser) {
    LOGGER.debug("Checking if username \'{}\' does match format", testUser.getUsername());

    return testUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(UserDTO testUser) {
    LOGGER.debug("Checking if email \'{}\' is unique", testUser.getEmail());

    return !userRepository.findByEmailIgnoreCase(testUser.getEmail()).isPresent();
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

  @Transactional
  public UserDTO signUpNewUser(UserDTO newUserDTO) {
    if (!isUserValid(newUserDTO)) {
      throw new IllegalArgumentException("An error occured. Please check your details!");
    }

    User persistedUser = userService.save(newUserDTO);

    LOGGER.info("New user signed up! username: '{}'; email: '{}'", persistedUser.getUsername(),
        persistedUser.getEmail());

    sendConfirmationEmailIfEmailIsEnabled(persistedUser);

    return userMapper.toDto(persistedUser);
  }

  public boolean isUserValid(UserDTO testUser) {
    LOGGER.debug("Checking if user \'{}\' is valid", testUser);

    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser)
        && isEmailAddressUnique(testUser) && isEmailAddress(testUser)
        && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
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
