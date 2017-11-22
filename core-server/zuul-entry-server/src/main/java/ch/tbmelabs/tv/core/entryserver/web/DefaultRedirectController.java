package ch.tbmelabs.tv.core.entryserver.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRedirectController {
  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String redirectToWebapp() {
    return "redirect:/webapp";
  }
}