package ch.tbmelabs.tv.webapp.account.passwordreset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.tbmelabs.tv.webapp.model.Account;

@Controller
public class PasswordResetController {
  @Autowired
  PasswordResetService passwordResetService;

  @RequestMapping(value = { "/login/reset-password" }, method = RequestMethod.POST)
  public void getPasswordResetToken(@RequestBody Account forgetfulUser) {
    passwordResetService.createPasswordResetTokenFromEmail(forgetfulUser);
  }

  @RequestMapping(value = { "/login/reset-password/{key}" }, method = RequestMethod.GET)
  public void isResetTokenValid(@PathVariable(name = "key") String key) {
    if (!passwordResetService.isTokenValid(key)) {
      throw new IllegalArgumentException("Password reset token expired!");
    }
  }

  @RequestMapping(value = { "/login/reset-password/{key}" }, method = RequestMethod.POST)
  public void resetPassword(@PathVariable(name = "key") String key, @RequestBody Account updatedAccount) {
    passwordResetService.resetPassword(key, updatedAccount);
  }
}