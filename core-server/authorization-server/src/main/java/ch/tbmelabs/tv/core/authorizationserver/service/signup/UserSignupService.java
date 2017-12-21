package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.Arrays;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Service
public class UserSignupService {
  private static final String USERNAME_REGEX = "^[A-Za-z0-9_-]{5,64}";
  private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  public boolean isUsernameUnique(User testUser) {
    return userRepository.findByUsername(testUser.getUsername()) == null;
  }

  public boolean doesUsernameMatchFormat(User newUser) {
    return newUser.getUsername().matches(USERNAME_REGEX);
  }

  public boolean isEmailAddressUnique(User newUser) {
    return userRepository.findByEmail(newUser.getEmail()) == null;
  }

  public boolean isEmailAddress(User testUser) {
    return EmailValidator.getInstance().isValid(testUser.getEmail());
  }

  public boolean doesPasswordMatchFormat(User testUser) {
    return testUser.getPassword().matches(PASSWORD_REGEX);
  }

  public boolean doPasswordsMatch(User testUser) {
    return testUser.getConfirmation().equals(testUser.getPassword());
  }

  public boolean isUserValid(User testUser) {
    return isUsernameUnique(testUser) && doesUsernameMatchFormat(testUser) && isEmailAddressUnique(testUser)
        && isEmailAddress(testUser) && doesPasswordMatchFormat(testUser) && doPasswordsMatch(testUser);
  }

  public User signUpNewUser(User newUser) {
    if (!isUserValid(newUser)) {
      throw new IllegalArgumentException("Registration failed. Please check your details!");
    }

    if (newUser.getGrantedAuthorities() == null || newUser.getGrantedAuthorities().isEmpty()) {
      newUser.setGrantedAuthorities(
          newUser.rolesToAssociations(Arrays.asList(roleRepository.findByName(SecurityRole.USER))));
    }

    return userRepository.save(newUser);
  }
}