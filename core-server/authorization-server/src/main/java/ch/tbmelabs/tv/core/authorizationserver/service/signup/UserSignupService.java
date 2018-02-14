package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.Arrays;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Service
public class UserSignupService {
  private static final Logger LOGGER = LogManager.getLogger(UserSignupService.class);

  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,64}";
  private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  public boolean isUsernameUnique(User testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" is unique");

    return userRepository.findByUsername(testUser.getUsername()) == null;
  }

  public boolean doesUsernameMatchFormat(User testUser) {
    LOGGER.debug("Checking if username \"" + testUser.getUsername() + "\" does match format");

    return testUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(User testUser) {
    LOGGER.debug("Checking if email \"" + testUser.getEmail() + "\" is unique");

    return userRepository.findByEmail(testUser.getEmail()) == null;
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
      throw new IllegalArgumentException("Registration failed. Please check your details!");
    }

    LOGGER.info("New user signed up! username: " + newUser.getUsername() + " and email: " + newUser.getEmail());

    if (newUser.getGrantedAuthorities() == null || newUser.getGrantedAuthorities().isEmpty()) {
      try {
        newUser.setGrantedAuthorities(
            newUser.rolesToAssociations(Arrays.asList(roleRepository.findByName(SecurityRole.USER))));
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("Unable to find default security role \"" + SecurityRole.USER + "\"!");
      }
    }

    return userRepository.save(newUser);
  }

  private boolean isUserValid(User testUser) {
    LOGGER.debug("Checking if user \"" + testUser + "\" is valid");

    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser) && isEmailAddressUnique(testUser)
        && isEmailAddress(testUser) && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
  }
}