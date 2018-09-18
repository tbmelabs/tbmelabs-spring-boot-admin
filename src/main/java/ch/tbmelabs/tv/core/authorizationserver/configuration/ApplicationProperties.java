package ch.tbmelabs.tv.core.authorizationserver.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
public class ApplicationProperties {

  private Server server;
  private Spring spring;

  @Data
  public static class Server {

    private String address;
    private Integer port;
    private String contextPath;

    private Ssl ssl;

    @Data
    public static class Ssl {

      private boolean enabled;
    }
  }

  @Data
  public static class Spring {

    private Mail mail;

    @Data
    public static class Mail {

      private String username;
    }
  }
}
