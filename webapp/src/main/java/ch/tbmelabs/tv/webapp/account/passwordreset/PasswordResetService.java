package ch.tbmelabs.tv.webapp.account.passwordreset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Timer;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.webapp.account.registration.AccountRegistrationService;
import ch.tbmelabs.tv.webapp.email.EmailService;
import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.PasswordResetToken;
import ch.tbmelabs.tv.webapp.model.PasswordResetToken.PasswordResetTokenRemover;
import ch.tbmelabs.tv.webapp.model.repository.AccountCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.PasswordResetTokenCRUDRepository;
import ch.tbmelabs.tv.webapp.security.token.SecurityTokenGenerator;

@Service
@Transactional(rollbackOn = { Exception.class })
public class PasswordResetService {
  private static final Logger LOGGER = LogManager.getLogger(PasswordResetService.class);

  private static String passwordResetRoute = "#/login/reset-password";
  private static String resetTokenGETArgument = "?resetToken=";
  private static String emailTemplateLocation = "email/password-reset.email.html";

  @Value("${server.url.basic}")
  private String basicServerUrl;

  @Autowired
  private EmailService mailService;

  @Autowired
  private AccountCRUDRepository accountRepository;

  @Autowired
  private PasswordResetTokenCRUDRepository passwordResetTokenRepository;

  public void createPasswordResetTokenFromEmail(Account account) {
    LOGGER.info("Password reset for email \"" + account.getEmail() + "\" requested");

    Account forgetfulUser;
    if ((forgetfulUser = accountRepository.findByEmail(account.getEmail())) == null) {
      LOGGER.warn("No user associated to email " + account.getEmail() + " found!");
      return;
    } else {
      LOGGER.debug("Forgetful user is " + forgetfulUser.getUsername() + " with id " + forgetfulUser.getId());
    }

    writeResetEmail(forgetfulUser);
  }

  public boolean isTokenValid(String token) {
    LOGGER.info("Checking if token " + token + " is present");

    return passwordResetTokenRepository.findByToken(token) != null;
  }

  public void resetPassword(String token, Account account) {
    if (account.doesPasswordMatchFormat() && account.doPasswordsMatch()) {
      LOGGER.info("Reseting password with token " + token);

      PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

      Account associatedUser = passwordResetToken.getAccount();
      associatedUser.setPassword(account.getPassword());
      accountRepository.save(associatedUser);

      passwordResetTokenRepository.delete(passwordResetToken);
    }
  }

  private void writeResetEmail(Account account) {
    LOGGER.debug("Creating password-reset email");

    PasswordResetToken resetToken = createToken(account);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
        new File(AccountRegistrationService.class.getClassLoader().getResource(emailTemplateLocation).toURI()))))) {
      StringBuilder htmlBuilder = new StringBuilder();
      String htmlLine;
      while ((htmlLine = reader.readLine()) != null) {
        htmlBuilder.append(htmlLine);
      }

      mailService.sendMail(account, "Reset Password of TBMELabs TV",
          prepareEmailBody(htmlBuilder.toString(), account, resetToken));
    } catch (IOException | URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String prepareEmailBody(String emailBody, Account account, PasswordResetToken token) {
    LOGGER.debug(
        "Preparing email body: username is " + account.getUsername() + " and associated url " + getResetUrl(token));

    String finalBody = emailBody.replaceAll("%USERNAME%", account.getUsername()).replaceAll("%RESET_URL%",
        getResetUrl(token));

    LOGGER.debug("Final email body is: " + finalBody);

    return finalBody;
  }

  private PasswordResetToken createToken(Account account) {
    LOGGER.debug("Assigning new password reset token to user");

    PasswordResetToken resetToken;
    try {
      if ((resetToken = passwordResetTokenRepository.findByAccount(account)) == null) {
        resetToken = new PasswordResetToken();
      }
    } catch (InvalidDataAccessResourceUsageException e) {
      LOGGER.error(e);

      resetToken = new PasswordResetToken();
    }

    resetToken.setToken(createUniqueResetToken());
    resetToken.setAccount(account);

    passwordResetTokenRepository.save(resetToken);
    new Timer().schedule(new PasswordResetTokenRemover(passwordResetTokenRepository, resetToken),
        resetToken.getExpirationDate());

    return resetToken;
  }

  private String createUniqueResetToken() {
    LOGGER.debug("Creating a singleton-password-reset-token");

    String newKey = SecurityTokenGenerator.getSecurityTokenString();

    if (passwordResetTokenRepository.findByToken(newKey) != null) {
      return createUniqueResetToken();
    }

    return newKey;
  }

  private String getResetUrl(PasswordResetToken token) {
    LOGGER.debug("Creating url to reset password");

    return basicServerUrl + passwordResetRoute + resetTokenGETArgument + token.getToken();
  }
}