package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import ch.tbmelabs.tv.core.authorizationserver.ssr.AngularUniversalRenderEngine;

@Configuration
public class AngularUniversalRenderEngineConfiguration {
  private ResourceLoader resourceLoader;

  public AngularUniversalRenderEngineConfiguration(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Bean
  public AngularUniversalRenderEngine angularUniversalRenderEngine() throws IOException {
    return new AngularUniversalRenderEngine(
        resourceLoader.getResource("classpath:/server-side-rendering/server.js").getFile());
  }
}
