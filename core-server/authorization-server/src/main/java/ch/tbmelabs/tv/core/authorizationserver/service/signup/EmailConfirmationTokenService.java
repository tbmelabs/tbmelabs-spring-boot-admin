package ch.tbmelabs.tv.core.authorizationserver.service.signup;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;

@Service
public class EmailConfirmationTokenService {
  @Autowired
  private EmailConfirmationTokenCRUDRepository emailConfirmationTokenRepository;

  public String createUniqueEmailConfirmationToken() {
    String token = UUID.randomUUID().toString();

    if (emailConfirmationTokenRepository.findByTokenString(token) != null) {
      return createUniqueEmailConfirmationToken();
    }

    return token;
  }
}