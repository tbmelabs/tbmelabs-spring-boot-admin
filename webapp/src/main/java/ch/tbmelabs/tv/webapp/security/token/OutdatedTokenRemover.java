package ch.tbmelabs.tv.webapp.security.token;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.webapp.model.AccountRegistrationToken;
import ch.tbmelabs.tv.webapp.model.PasswordResetToken;
import ch.tbmelabs.tv.webapp.model.repository.AccountRegistrationTokenCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.PasswordResetTokenCRUDRepository;

@Component
public class OutdatedTokenRemover implements ApplicationListener<ApplicationReadyEvent> {
  private static final Logger LOGGER = LogManager.getLogger(OutdatedTokenRemover.class);

  @Autowired
  private AccountRegistrationTokenCRUDRepository registrationTokenRepository;

  @Autowired
  private PasswordResetTokenCRUDRepository passwordResetTokenRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    LOGGER.info("Looking for outdated " + AccountRegistrationToken.class + " or " + PasswordResetToken.class);

    registrationTokenRepository.findAll().forEach(accountRegistrationToken -> {
      if (accountRegistrationToken.getExpirationDate().before(new Date())) {
        LOGGER.warn("Deleting outdated registration-token " + accountRegistrationToken.getId());

        registrationTokenRepository.delete(accountRegistrationToken);
      }
    });

    passwordResetTokenRepository.findAll().forEach(passwordResetToken -> {
      if (passwordResetToken.getExpirationDate().before(new Date())) {
        LOGGER.warn("Deleting outdated password-reset-token " + passwordResetToken.getId());

        passwordResetTokenRepository.delete(passwordResetToken);
      }
    });
  }
}