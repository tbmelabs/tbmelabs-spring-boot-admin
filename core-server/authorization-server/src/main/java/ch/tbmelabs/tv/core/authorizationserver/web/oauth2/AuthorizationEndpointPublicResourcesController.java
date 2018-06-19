package ch.tbmelabs.tv.core.authorizationserver.web.oauth2;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/oauth/public"})
public class AuthorizationEndpointPublicResourcesController {

  @Autowired
  private ResourceLoader resourceLoader;

  @GetMapping(path = {"/{filename:(?:\\w|\\W)+\\.(?:js)$}"})
  public File getPublicJavascriptResource(@PathVariable String filename,
      HttpServletResponse httpServletResponse) throws IOException {
    return resourceLoader.getResource("classpath:static/public/" + filename).getFile();
  }
}
