package ch.tbmelabs.tv.services.userservice.whoami;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhoAmIController {
  @RequestMapping("/whoami")
  public Principal whoAmI(Principal principal) {
    return principal;
  }
}