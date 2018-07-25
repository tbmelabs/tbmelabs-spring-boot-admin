package ch.tbmelabs.tv.core.servicediscovery.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
