package ch.tbmelabs.tv.core.entryserver.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRedirectController {
  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String redirectToWebapp(HttpServletResponse response) throws IOException {
    // response.sendRedirect("/webapp");

    return UUID.randomUUID().toString();
  }
}