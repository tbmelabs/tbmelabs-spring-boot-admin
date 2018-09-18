package ch.tbmelabs.tv.core.authorizationserver.web.signup;

import ch.tbmelabs.tv.core.authorizationserver.domain.dto.UserDTO;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

  private UserSignupService signupService;

  public SignupController(UserSignupService userSignupService) {
    this.signupService = userSignupService;
  }

  @PostMapping({"/do-signup"})
  public UserDTO signup(@RequestBody UserDTO newUserDTO) {
    return signupService.signUpNewUser(newUserDTO);
  }

  @PostMapping({"/is-username-unique"})
  public ResponseEntity<IllegalArgumentException> isUsernameUnique(
    @RequestBody UserDTO newUser) {
    if (!signupService.isUsernameUnique(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("Username already exists!"));
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping({"/does-username-match-format"})
  public ResponseEntity<IllegalArgumentException> doesUsernameMatchFormat(
    @RequestBody UserDTO newUser) {
    if (!signupService.doesUsernameMatchFormat(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("Username does not match format!"));
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping({"/is-email-unique"})
  public ResponseEntity<IllegalArgumentException> isEmailAddressUnique(
    @RequestBody UserDTO newUser) {
    if (!signupService.isEmailAddressUnique(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("E-mail address already in use!"));
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping({"/is-email"})
  public ResponseEntity<IllegalArgumentException> isEmailAddress(
    @RequestBody UserDTO newUser) {
    if (!signupService.isEmailAddress(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("Not a valid e-mail address!"));
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping({"/does-password-match-format"})
  public ResponseEntity<IllegalArgumentException> doesPasswordMatchFormat(
    @RequestBody UserDTO newUser) {
    if (!signupService.doesPasswordMatchFormat(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("Password does not match format!"));
    }

    return ResponseEntity.ok().build();
  }

  @PostMapping({"/do-passwords-match"})
  public ResponseEntity<IllegalArgumentException> doPasswordsMatch(
    @RequestBody UserDTO newUser) {
    if (!signupService.doPasswordsMatch(newUser)) {
      return ResponseEntity.badRequest()
        .body(new IllegalArgumentException("Passwords do not match!"));
    }

    return ResponseEntity.ok().build();
  }
}
