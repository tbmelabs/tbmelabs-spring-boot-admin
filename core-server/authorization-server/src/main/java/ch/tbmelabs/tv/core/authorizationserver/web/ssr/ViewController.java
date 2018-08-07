package ch.tbmelabs.tv.core.authorizationserver.web.ssr;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ch.tbmelabs.tv.core.authorizationserver.ssr.AngularUniversalRenderEngine;

@Controller
public class ViewController {
  private AngularUniversalRenderEngine angularUniversalRenderEngine;

  public ViewController(AngularUniversalRenderEngine angularUniversalRenderEngine) {
    this.angularUniversalRenderEngine = angularUniversalRenderEngine;
  }

  @GetMapping("/")
  public String indexView() throws InterruptedException, ExecutionException {
    return angularUniversalRenderEngine.renderUri("/").get();
  }
}
