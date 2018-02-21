package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;

@Service
public class EmailConfirmationTokenService {
  @Autowired
  private EmailConfirmationTokenCRUDRepository emailConfirmationTokenRepository;

  public EmailConfirmationToken createUniqueEmailConfirmationToken(User user) {
    String token = UUID.randomUUID().toString();

    if (emailConfirmationTokenRepository.findByTokenString(token) != null) {
      return createUniqueEmailConfirmationToken(user);
    }

    return emailConfirmationTokenRepository.save(new EmailConfirmationToken(token, user));
  }
}