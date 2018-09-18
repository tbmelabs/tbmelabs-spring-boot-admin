package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AngularUniversalRenderEngineConfiguration {

  @Bean
  public ThreadPoolTaskExecutor angularUniversalRenderingExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix("angular-universal-rendering-executor-");
    threadPoolTaskExecutor.setCorePoolSize(2);
    threadPoolTaskExecutor.setMaxPoolSize(2);
    threadPoolTaskExecutor.afterPropertiesSet();
    return threadPoolTaskExecutor;
  }
}
