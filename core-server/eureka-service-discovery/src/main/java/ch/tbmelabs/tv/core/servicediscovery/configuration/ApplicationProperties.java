package ch.tbmelabs.tv.core.servicediscovery.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties
public class ApplicationProperties {

  private Eureka eureka;

  @Data
  public static class Eureka {

    private Administrator administrator;

    @Data
    public static class Administrator {
      private String name;
      private String password;
    }
  }
}
