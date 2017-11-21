package ch.tbmelabs.tv.core.entryserver.service.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @RequestMapping("/test")
  public String getRandomID() {
    return UUID.randomUUID().toString();
  }
}
