package ch.tbmelabs.tv.core.authenticationserver.web.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.tbmelabs.tv.core.authenticationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.shared.domain.authentication.user.User;

@Controller
public class SignupController {
  @Autowired
  private UserSignupService signupService;

  @RequestMapping(value = { "/signup" }, method = { RequestMethod.POST })
  public User signup(@RequestBody(required = true) User newUser) {
    if (!signupService.isUserValid(newUser)) {
      throw new IllegalArgumentException("Registration failed. Please check your details!");
    }

    return signupService.signUpNewUser(newUser);
  }

  @RequestMapping(value = { "/signup/is-username-unique" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> isUsernameUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isUsernameUnique(newUser)) {
      throw new IllegalArgumentException("Username already exists!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = { "/signup/does-username-match-format" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> doesUsernameMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesUsernameMatchFormat(newUser)) {
      throw new IllegalArgumentException("Username does not match format!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = { "/signup/is-email-unique" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> isEmailAddressUnique(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddressUnique(newUser)) {
      throw new IllegalArgumentException("E-mail address already in use!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = { "/signup/is-email" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> isEmailAddress(@RequestBody(required = true) User newUser) {
    if (!signupService.isEmailAddress(newUser)) {
      throw new IllegalArgumentException("Not a valid e-mail address!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = { "/signup/does-password-match-format" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> doesPasswordMatchFormat(@RequestBody(required = true) User newUser) {
    if (!signupService.doesPasswordMatchFormat(newUser)) {
      throw new IllegalArgumentException("Password does not match format!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = { "/signup/do-passwords-match" }, method = { RequestMethod.POST })
  public ResponseEntity<HttpStatus> doPasswordsMatch(@RequestBody(required = true) User newUser) {
    if (!signupService.doPasswordsMatch(newUser)) {
      throw new IllegalArgumentException("Passwords do not match!");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }
}