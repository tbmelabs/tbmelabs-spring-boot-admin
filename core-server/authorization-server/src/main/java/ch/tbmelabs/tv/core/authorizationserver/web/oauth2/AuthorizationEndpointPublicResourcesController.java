package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth/public")
public class AuthorizationEndpointPublicResourcesController {
  @GetMapping("/{filename}")
  public void getPublicResource(@PathVariable String filename) {
    LogManager.getLogger(AuthorizationEndpointPublicResourcesController.class)
        .fatal("looking for file \"" + filename + "\" in "
            + AuthorizationEndpointPublicResourcesController.class
                .getResource("application.properties"));

    AuthorizationEndpointPublicResourcesController.class
        .getResourceAsStream("/static/public/" + filename);
  }
}
