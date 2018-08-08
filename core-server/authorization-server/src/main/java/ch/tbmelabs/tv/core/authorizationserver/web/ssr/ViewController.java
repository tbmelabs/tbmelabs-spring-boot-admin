package ch.tbmelabs.tv.core.authorizationserver.web.ssr;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.slf4j.LoggerFactory;
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
  public Callable<String> indexView() throws InterruptedException, ExecutionException {
    LoggerFactory.getLogger(ViewController.class).info("Async request mapping!!");

    return () -> angularUniversalRenderEngine.renderUri("/").get();
  }

  @GetMapping("/signin")
  public Callable<String> signinView() throws InterruptedException, ExecutionException {
    LoggerFactory.getLogger(ViewController.class).info("Async request mapping!!");

    return () -> angularUniversalRenderEngine.renderUri("/signin").get();
  }

  @GetMapping("/signup")
  public Callable<String> signupView() throws InterruptedException, ExecutionException {
    LoggerFactory.getLogger(ViewController.class).info("Async request mapping!!");

    return () -> angularUniversalRenderEngine.renderUri("/signup").get();
  }

  @GetMapping("/oauth/confirm_access")
  public Callable<String> oauthConfirmAccessView() throws InterruptedException, ExecutionException {
    LoggerFactory.getLogger(ViewController.class).info("Async request mapping!!");

    return () -> angularUniversalRenderEngine.renderUri("/oauth/confirm_access").get();
  }
}
