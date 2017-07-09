package ch.tbmelabs.tv.webapp.account.registration;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.tbmelabs.tv.webapp.model.Account;

@Controller
public class AccountRegistrationController {
  @Autowired
  private AccountValidationService validationService;

  @Autowired
  private AccountRegistrationService registrationService;

  @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
  @ResponseBody
  public Account registerNewAccount(@RequestBody Account newAccount) {
    return registrationService.registerAccount(newAccount);
  }

  @RequestMapping(value = { "/register/check/is-username-unique" }, method = RequestMethod.POST)
  public void checkUsernameExisting(@RequestBody Account account) {
    validationService.checkUsernameNotExisting(account.getUsername());
  }

  @RequestMapping(value = { "/register/check/is-email-unique" }, method = RequestMethod.POST)
  public void checkEmailRegistered(@RequestBody Account account) {
    validationService.checkEmailNotRegistered(account.getEmail());
  }

  @RequestMapping(value = { "/register/check/does-password-match-format" }, method = RequestMethod.POST)
  public void checkPasswordFormat(@RequestBody Account account) {
    account.doesPasswordMatchFormat();
  }

  @RequestMapping(value = { "/register/check/do-passwords-match" }, method = RequestMethod.POST)
  public void checkPasswordsMatch(@RequestBody Account account) {
    account.doPasswordsMatch();
  }

  @RequestMapping(value = { "/register/confirm/{confirmationToken}" }, method = RequestMethod.GET)
  @ResponseBody
  public void confirmRegistration(@PathVariable(name = "confirmationToken", required = true) String confirmationToken,
      HttpServletResponse response) throws IOException {
    String type = "danger";
    String message = "The token expired or is invalid!";

    if (registrationService.confirmRegistration(confirmationToken)) {
      type = "success";
      message = "Thank you for confirming your e-mail address.";
    }

    response.sendRedirect("/#/login/?type=" + type + "&text=" + message);
  }
}