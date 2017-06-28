package ch.tbmelabs.tv.webapp.account.registration;

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
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.webapp.email.EmailService;
import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.AccountRegistrationToken;
import ch.tbmelabs.tv.webapp.model.AccountRegistrationToken.AccountRegistrationTokenRemover;
import ch.tbmelabs.tv.webapp.model.repository.AccountCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.AccountRegistrationTokenCRUDRepository;
import ch.tbmelabs.tv.webapp.model.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.webapp.security.role.SecurityRole;
import ch.tbmelabs.tv.webapp.security.token.SecurityTokenGenerator;

@Service
@Transactional(rollbackOn = { Exception.class })
public class AccountRegistrationService {
  private static final Logger LOGGER = LogManager.getLogger(AccountRegistrationService.class);

  private static String confirmRegistrationRoute = "register/confirm";
  private static String emailTemplateLocation = "email/registration.email.html";

  @Value("${server.url.basic}")
  private String basicServerUrl;

  @Autowired
  private EmailService mailService;

  @Autowired
  private AccountValidationService validationService;

  @Autowired
  private AccountCRUDRepository accountRepository;

  @Autowired
  private RoleCRUDRepository roleRepository;

  @Autowired
  private AccountRegistrationTokenCRUDRepository registrationTokenRepository;

  public Account registerAccount(Account newAccount) {
    LOGGER.info("Registering new user " + newAccount.getUsername());

    validationService.validateNewAccount(newAccount);
    newAccount.setAccessLevel(roleRepository.findByRoleName(SecurityRole.ROLE_USER));
    accountRepository.save(newAccount);

    writeEmailConfirmation(newAccount, createConfirmationToken(newAccount));

    return newAccount;
  }

  public Account confirmRegistration(String token) {
    AccountRegistrationToken registrationToken = registrationTokenRepository.findByToken(token);

    Account account = registrationToken.getAccount();
    account.setIsEmailApproved(true);

    LOGGER.info("Confirming registration for user " + account.getUsername());

    registrationTokenRepository.delete(registrationToken);

    return account;
  }

  private void writeEmailConfirmation(Account account, AccountRegistrationToken token) {
    LOGGER.debug("Creating confirmation email");

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
        new File(AccountRegistrationService.class.getClassLoader().getResource(emailTemplateLocation).toURI()))))) {
      StringBuilder htmlBuilder = new StringBuilder();
      String htmlLine;
      while ((htmlLine = reader.readLine()) != null) {
        htmlBuilder.append(htmlLine);
      }

      mailService.sendMail(account, "Registration to TBMELabs TV",
          prepareEmailBody(htmlBuilder.toString(), account, token));
    } catch (IOException | URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private String prepareEmailBody(String emailBody, Account account, AccountRegistrationToken token) {
    LOGGER.debug("Preparing email body: username is " + account.getUsername() + " and associated url "
        + getConfirmationUrl(token));

    emailBody = emailBody.replaceAll("%USERNAME%", account.getUsername());
    emailBody = emailBody.replaceAll("%CONFIRMATION_URL%", getConfirmationUrl(token));

    LOGGER.debug("Final email body is: " + emailBody);

    return emailBody;
  }

  private AccountRegistrationToken createConfirmationToken(Account account) {
    LOGGER.debug("Assigning new confirmation token to user");

    AccountRegistrationToken registrationToken;
    if ((registrationToken = registrationTokenRepository.findByAccount(account)) == null) {
      registrationToken = new AccountRegistrationToken();
    }

    registrationToken.setToken(createUniqueConfirmationToken());
    registrationToken.setAccount(account);

    registrationTokenRepository.save(registrationToken);
    new Timer().schedule(new AccountRegistrationTokenRemover(registrationTokenRepository, registrationToken),
        registrationToken.getExpirationDate());

    return registrationToken;
  }

  private String createUniqueConfirmationToken() {
    LOGGER.debug("Creating singleton-registration-token");

    String newKey = SecurityTokenGenerator.getSecurityTokenString();

    if (registrationTokenRepository.findByToken(newKey) != null) {
      return createUniqueConfirmationToken();
    }

    return newKey;
  }

  private String getConfirmationUrl(AccountRegistrationToken token) {
    LOGGER.debug("Creating url to confirm registration");

    return basicServerUrl + confirmRegistrationRoute + "/" + token.getToken();
  }
}