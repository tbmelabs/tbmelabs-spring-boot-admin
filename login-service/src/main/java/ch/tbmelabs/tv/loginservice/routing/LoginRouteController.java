package ch.tbmelabs.tv.loginservice.routing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginRouteController {
  @Value("${authorization-server.base-uri}")
  private String authServerBaseUri;

  @RequestMapping("/")
  public void redirectLoginAttempt() {
RestTemplate template= new RestTemplate();
  template.
  }
}