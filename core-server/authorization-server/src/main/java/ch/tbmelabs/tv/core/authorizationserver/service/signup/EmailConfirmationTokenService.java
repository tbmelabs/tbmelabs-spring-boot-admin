package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.exception.EmailConfirmationTokenNotFoundException;

public interface EmailConfirmationTokenService {

  String createUniqueEmailConfirmationToken(User user);

  void confirmRegistration(String token) throws EmailConfirmationTokenNotFoundException;
}
