package ch.tbmelabs.tv.core.entryserver.web;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String mapRootUri() throws IOException {
    return "Root: " + UUID.randomUUID().toString();
  }

  @RequestMapping(value = { "/public" }, method = RequestMethod.GET)
  public String mapPublicUri() throws IOException {
    return "Public: " + UUID.randomUUID().toString();
  }

  @RequestMapping(value = { "/test" }, method = RequestMethod.GET)
  public String mapSecuredTestUri() throws IOException {
    return "Public: " + UUID.randomUUID().toString();
  }
}