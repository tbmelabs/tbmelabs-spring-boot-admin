package ch.tbmelabs.tv.shared.securityutils.configuration;

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

    private Instance instance;

    @Data
    public static class Instance {

      private MetadataMap metadataMap;

      @Data
      public static class MetadataMap {

        private User user;

        @Data
        public static class User {
          private String name;
          private String password;
        }
      }
    }
  }
}
