package ch.tbmelabs.tv.core.authorizationserver.web.signup;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupConfirmationController {
  public static final String CONFIRMATION_ENDPOINT = "/signup/confirm";
  public static final String TOKEN_ARGUMENT_NAME = "confirmation_token";
}