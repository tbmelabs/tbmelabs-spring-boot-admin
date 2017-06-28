package ch.tbmelabs.tv.webapp.account.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.webapp.model.Account;
import ch.tbmelabs.tv.webapp.model.repository.AccountCRUDRepository;

@Service
public class AccountValidationService {
  private static final Logger LOGGER = LogManager.getLogger(AccountValidationService.class);

  @Autowired
  private AccountCRUDRepository accountRepository;

  public void validateNewAccount(Account newAccount) {
    checkAttributesSet(newAccount);
    checkUsernameNotExisting(newAccount.getUsername());
  }

  public void checkAttributesSet(Account account) {
    List<String> errorList = new ArrayList<>();

    if (account.getUsername() == null) {
      errorList.add("username");
    }

    if (account.getEmail() == null) {
      errorList.add("email");
    }

    if (account.getPassword() == null) {
      errorList.add("password");
    }

    if (account.getPasswordMatch() == null) {
      errorList.add("matching password");
    }

    if (!errorList.isEmpty()) {
      throw new IllegalArgumentException(
          "Not all required attributes present! Missing fields are: " + toReadableStringList(errorList));
    }
  }

  private String toReadableStringList(List<String> strings) {
    StringBuilder errorMessage = new StringBuilder();

    for (int i = 0; i < strings.size(); i++) {
      if (i == strings.size() - 1) {
        errorMessage.append(strings.get(i));
      } else {
        errorMessage.append(strings.get(i) + ", ");
      }
    }

    return errorMessage.toString();
  }

  public void checkUsernameNotExisting(String username) {
    LOGGER.info("Checking if username \"" + username + "\" already exists");

    if (isUsernameExisting(username)) {
      throw new IllegalArgumentException("Username already exists!");
    }
  }

  public void checkEmailNotRegistered(String email) {
    LOGGER.info("Checking if email \"" + email + "\" already exists");

    if (isEmailRegistered(email)) {
      throw new IllegalArgumentException("E-Mail already registered!");
    }
  }

  public boolean isUsernameExisting(String username) {
    return accountRepository.findByUsername(username) != null;
  }

  public boolean isEmailRegistered(String email) {
    return accountRepository.findByEmail(email) != null;
  }
}