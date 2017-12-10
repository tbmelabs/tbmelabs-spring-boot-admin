package ch.tbmelabs.tv.core.authorizationserver.web.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@RestController
public class SignupController {
  @Autowired
  private UserSignupService signupService;

  @RequestMapping(value = { "/signup" }, method = { RequestMethod.POST })
  public User signup(@RequestBody(required = true) User newUser) {
    return signupService.signUpNewUser(newUser);
  }

  @RequestMapping(value = { "/signup/is-username-unique" }, method = { RequestMethod.POST })
  public void isUsernameUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isUsernameUnique(newUser)) {
      throw new IllegalArgumentException("Username already exists!");
    }
  }

  @RequestMapping(value = { "/signup/does-username-match-format" }, method = { RequestMethod.POST })
  public void doesUsernameMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesUsernameMatchFormat(newUser)) {
      throw new IllegalArgumentException("Username does not match format!");
    }
  }

  @RequestMapping(value = { "/signup/is-email-unique" }, method = { RequestMethod.POST })
  public void isEmailAddressUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddressUnique(newUser)) {
      throw new IllegalArgumentException("E-mail address already in use!");
    }
  }

  @RequestMapping(value = { "/signup/is-email" }, method = { RequestMethod.POST })
  public void isEmailAddress(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddress(newUser)) {
      throw new IllegalArgumentException("Not a valid e-mail address!");
    }
  }

  @RequestMapping(value = { "/signup/does-password-match-format" }, method = { RequestMethod.POST })
  public void doesPasswordMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesPasswordMatchFormat(newUser)) {
      throw new IllegalArgumentException("Password does not match format!");
    }
  }

  @RequestMapping(value = { "/signup/do-passwords-match" }, method = { RequestMethod.POST })
  public void doPasswordsMatch(@RequestBody(required = true) User newUser) {
    if (!signupService.doPasswordsMatch(newUser)) {
      throw new IllegalArgumentException("Passwords do not match!");
    }
  }
}