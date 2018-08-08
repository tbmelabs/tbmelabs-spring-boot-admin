package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ch.tbmelabs.tv.core.authorizationserver.ssr.AngularUniversalRenderEngine;

@EnableAsync
@Configuration
public class AngularUniversalRenderEngineConfiguration {
  private ResourceLoader resourceLoader;

  public AngularUniversalRenderEngineConfiguration(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Bean
  public AngularUniversalRenderEngine angularUniversalRenderEngine() throws IOException {
    return new AngularUniversalRenderEngine(
        resourceLoader.getResource("classpath:/server-side-rendering/server.js").getFile(),
        angularUniversalRenderEngineExecutor());
  }

  @Bean
  @Qualifier("angularUniversalRenderEngineExecutor")
  public ThreadPoolTaskExecutor angularUniversalRenderEngineExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("AngularUniversalRenderEngine-");
    executor.initialize();
    return executor;
  }
}
