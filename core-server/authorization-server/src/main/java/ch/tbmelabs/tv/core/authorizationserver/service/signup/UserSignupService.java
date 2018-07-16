package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;

public interface UserSignupService {

  boolean isUsernameUnique(UserDTO testUser);

  boolean doesUsernameMatchFormat(UserDTO testUser);

  boolean isEmailAddressUnique(UserDTO testUser);

  boolean isEmailAddress(UserDTO testUser);

  boolean doesPasswordMatchFormat(UserDTO testUser);

  boolean doPasswordsMatch(UserDTO testUser);

  UserDTO signUpNewUser(UserDTO newUserDTO);

  boolean isUserValid(UserDTO testUser);

  UserDTO setDefaultRolesIfNonePresent(UserDTO newUserDTO);

  User sendConfirmationEmailIfEmailIsEnabled(User persistedUser);
}
