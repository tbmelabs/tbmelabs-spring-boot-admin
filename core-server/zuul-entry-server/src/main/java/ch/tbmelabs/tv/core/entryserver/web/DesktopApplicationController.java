package ch.tbmelabs.tv.core.entryserver.web;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/"})
public class DesktopApplicationController {

  @GetMapping
  public String getDesktopApplication(Principal principal) {
    if (principal == null) {
      return "landing-page.html";
    }

    return "index.html";
  }
}
