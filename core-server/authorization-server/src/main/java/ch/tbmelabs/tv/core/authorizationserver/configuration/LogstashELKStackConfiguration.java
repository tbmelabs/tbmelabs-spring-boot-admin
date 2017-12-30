package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.SocketAppender;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@Profile({ SpringApplicationProfile.ELK })
public class LogstashELKStackConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(LogstashELKStackConfiguration.class);

  private static final Integer BUFFER_SIZE = 2048;

  @Value("${LOGSTASH_HOST}")
  private String logstashHost;

  @Value("${LOGSTASH_PORT}")
  private Integer logstashPort;

  @PostConstruct
  public void initBean() {
    LOGGER.info("Configuring new " + SocketAppender.class + " with Logstash and ELK stack");

    // @formatter:off
    SocketAppender logstashAppender = SocketAppender.newBuilder()
        .withHost(logstashHost)
        .withPort(logstashPort)
        .withReconnectDelayMillis(-1)
        .withName("logstash")
        .withImmediateFail(false)
        .withBufferSize(BUFFER_SIZE)
        .withLayout(JsonLayout.createDefaultLayout())
        .build();
    // @formatter:on

    logstashAppender.start();
  }

  @Component
  @Profile({ SpringApplicationProfile.PROD })
  private class ProducitveEnvironmentWithoutLogstashCheck {
    @Autowired
    private Environment env;

    @PostConstruct
    public void initBean() {
      if (!Arrays.stream(env.getActiveProfiles()).filter(env -> env.equals(SpringApplicationProfile.ELK)).findAny()
          .isPresent()) {
        LOGGER.warn("!!! ------------------------------------------------------- !!!");
        LOGGER.warn("!!! -------- Found active profile " + SpringApplicationProfile.PROD + " without "
            + SpringApplicationProfile.ELK + " -------- !!!");
        LOGGER.warn("!!! Consider monitoring your application with the ELK stack !!!");
        LOGGER.warn("!!! ------------------------------------------------------- !!!");
        LOGGER.warn("!!! ------------------------------------------------------- !!!");
      }
    }
  }
}